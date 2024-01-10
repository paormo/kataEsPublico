package com.example.prueba.es.publico.application.service;
import com.example.prueba.es.publico.application.exception.ErrorWritingException;
import com.example.prueba.es.publico.domain.model.PageOrder;
import com.example.prueba.es.publico.domain.model.Order;
import com.example.prueba.es.publico.domain.ports.external.EsPublicoApiPort;
import com.example.prueba.es.publico.domain.ports.internal.OrderPersistencePort;
import com.example.prueba.es.publico.domain.service.OrderImportService;
import com.example.prueba.es.publico.infrastructure.external.espublico.EsPublicoApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.example.prueba.es.publico.application.exception.ExceptionUtils.handlingConsumerWrapper;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderImportServiceImpl implements OrderImportService {

    private static final Integer START_PAGE = 0;
    private static final Integer MAX_PER_PAGE = 1000;
    private static final Integer PAGE_SIZE_TO_FIND = 300000;
    public static final String DELIMITER = ",";
    private final EsPublicoApiClient esPublicoApi;
    private final EsPublicoApiPort esPublicoApiPort;
    private final OrderPersistencePort orderPersistencePort;
    private static final String[] CSV_HEADERS = {"Order ID",
            "Order Priority",
            "Order Date",
            "Region",
            "Country",
            "Item Type",
            "Sales Channel",
            "Ship Date",
            "Units Sold",
            "Unit Price",
            "Unit Cost",
            "Total Revenue",
            "Total Cost",
            "Total Profit"};
    private static final String[] TOTALS_FIELDS_NAMES = {"Region",
            "Country",
            "Item Type",
            "Sales Channel",
            "Order Priority"};
    private static final BufferedWriter bufferedWriter ;

    @Override
    public String importOrdersAndGenerateCSV() throws IOException {
        importFromAPIToPersistence();
        generateOrdersCSV();
        return new File("").getAbsolutePath();
    }

    static {
        try {
            bufferedWriter = Files.newBufferedWriter(Path.of("result.csv"));
        } catch (final IOException e) {
            log.error( "OrderImportServiceImpl.static Error initializing bufferedWriter : \n{}", e.getMessage());
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    private void importOrdersRecursive(String url) {
        if (url != null) {
            PageOrder pageOrder;
            pageOrder = esPublicoApi.getPageOrderByUrl(url);
            CompletableFuture.runAsync(()->orderPersistencePort.saveInPersistence(pageOrder.getContent()));

            importOrdersRecursive(pageOrder.getLinks().getNext());
        }
    }

    private void importFromAPIToPersistence() {
        PageOrder pageOrder;
        pageOrder = esPublicoApi.getPageOrderByPageAndMaxPerPage(START_PAGE, MAX_PER_PAGE);
        orderPersistencePort.saveInPersistence(pageOrder.getContent());
        importOrdersRecursive(pageOrder.getLinks().getNext());
    }

    private void generateOrdersCSV() {
        Map<String, Map<String, Long>> fieldsValuesCountsMap = createTotalsMap();
        try {
            bufferedWriter.write(String.join(DELIMITER, CSV_HEADERS));

            findAndWriteDataLines(fieldsValuesCountsMap, START_PAGE, PAGE_SIZE_TO_FIND);

            writeTotalsLines(fieldsValuesCountsMap);

            bufferedWriter.flush();
            bufferedWriter.close();}
        catch (IOException e){
            log.error( "OrderImportServiceImpl.generateOrdersCSV : Error writing in bufferedWriter : \n{}", e.getMessage());
            throw new ErrorWritingException(e);
        }
    }

    private void findAndWriteDataLines(Map<String, Map<String, Long>> totals, Integer page, Integer pageSize) {

        Page<Order> resultSet = orderPersistencePort.getPageFromPersistenceOrderedByOrderId(page, pageSize);
        List<String> csvLines = resultSet.stream().map(order -> addCountsToTotals(totals, order)).map(Order::toString).toList();
        if (!csvLines.isEmpty()) {
            csvLines.forEach(handlingConsumerWrapper(OrderImportServiceImpl::writeLine, IOException.class));
            findAndWriteDataLines(totals, resultSet.getPageable().getPageNumber() + 1, pageSize);
        }


    }

    private Order addCountsToTotals(Map<String, Map<String, Long>> totals, Order order) {
        Map<String, Long> regionTotalMap = totals.get(TOTALS_FIELDS_NAMES[0]);
        regionTotalMap.put(order.getRegion(), regionTotalMap.get(order.getRegion()) != null ? regionTotalMap.get(order.getRegion()) + 1L : 1L);

        Map<String, Long> countryTotalMap = totals.get(TOTALS_FIELDS_NAMES[1]);
        countryTotalMap.put(order.getCountry(), countryTotalMap.get(order.getCountry()) != null ? countryTotalMap.get(order.getCountry()) + 1L : 1L);

        Map<String, Long> itemTypeMap = totals.get(TOTALS_FIELDS_NAMES[2]);
        itemTypeMap.put(order.getItemType(), itemTypeMap.get(order.getItemType()) != null ? itemTypeMap.get(order.getItemType()) + 1L : 1L);

        Map<String, Long> salesChannelMap = totals.get(TOTALS_FIELDS_NAMES[3]);
        salesChannelMap.put(order.getSalesChannel(), salesChannelMap.get(order.getSalesChannel()) != null ? salesChannelMap.get(order.getSalesChannel()) + 1L : 1L);

        Map<String, Long> orderPriorityMap = totals.get(TOTALS_FIELDS_NAMES[4]);
        orderPriorityMap.put(order.getPriority(), orderPriorityMap.get(order.getPriority()) != null ? orderPriorityMap.get(order.getPriority()) + 1L : 1L);

        return order;
    }

    private void writeTotalsLines(Map<String, Map<String, Long>> totals) {
        totals.forEach((key, value) -> {
            try {
                writeLine(key);
                value.forEach((key1, value1) -> {
                    try {
                        StringBuilder builder = new StringBuilder();
                        builder.append(DELIMITER).append(key1).append(DELIMITER).append(value1);
                        writeLine(builder.toString());
                    } catch (IOException e) {
                        log.error( "OrderImportServiceImpl.writeTotalsLines(A) : Error writing in bufferedWriter : \n{}", e.getMessage());
                        throw new ErrorWritingException(e);
                    }
                });

            } catch (IOException e) {
                log.error( "OrderImportServiceImpl.writeTotalsLines(B) : Error writing in bufferedWriter : \n{}", e.getMessage());
                throw new ErrorWritingException(e);
            }
        });
    }

    private Map<String, Map<String, Long>> createTotalsMap() {
        Map<String, Map<String, Long>> newMap = new HashMap<>();
        Arrays.stream(TOTALS_FIELDS_NAMES).forEach(fieldName -> newMap.put(fieldName, new HashMap<>()));
        return newMap;
    }


    private static void writeLine(String line) throws IOException {
        bufferedWriter.newLine();
        bufferedWriter.write(line);
    }


}
