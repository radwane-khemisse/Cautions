package org.sid.stockservice.mapper;

import org.sid.stockservice.dto.StockDTO;
import org.sid.stockservice.entities.StockMarket;

public class StockMapper {
    private StockMapper() {
    }

    public static StockDTO toDto(StockMarket stock) {
        if (stock == null) return null;
        return StockDTO.builder()
                .id(stock.getId())
                .date(stock.getDate())
                .openValue(stock.getOpenValue())
                .highValue(stock.getHighValue())
                .lowValue(stock.getLowValue())
                .closeValue(stock.getCloseValue())
                .volume(stock.getVolume())
                .companyId(stock.getCompanyId())
                .build();
    }

    public static StockMarket toEntity(StockDTO dto) {
        if (dto == null) return null;
        return StockMarket.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .openValue(dto.getOpenValue())
                .highValue(dto.getHighValue())
                .lowValue(dto.getLowValue())
                .closeValue(dto.getCloseValue())
                .volume(dto.getVolume())
                .companyId(dto.getCompanyId())
                .build();
    }

    public static void updateEntity(StockMarket stock, StockDTO dto) {
        if (dto.getDate() != null) stock.setDate(dto.getDate());
        if (dto.getOpenValue() != null) stock.setOpenValue(dto.getOpenValue());
        if (dto.getHighValue() != null) stock.setHighValue(dto.getHighValue());
        if (dto.getLowValue() != null) stock.setLowValue(dto.getLowValue());
        if (dto.getCloseValue() != null) stock.setCloseValue(dto.getCloseValue());
        if (dto.getVolume() != null) stock.setVolume(dto.getVolume());
        if (dto.getCompanyId() != null) stock.setCompanyId(dto.getCompanyId());
    }
}
