package br.com.probelle.sistemas.probelle_tms.v1.services.edi;

import br.com.probelle.sistemas.probelle_tms.v1.entity.Manifest;
import br.com.probelle.sistemas.probelle_tms.v1.entity.ManifestInvoice;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotfisSerializer {

  private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
  private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HHmmss");

  public String serialize(Manifest manifest, List<ManifestInvoice> items) {
    LocalDateTime now = LocalDateTime.now();
    StringBuilder sb = new StringBuilder();
    sb.append("000|NOTFIS")
        .append("|MANIFEST_NO=")
        .append(manifest.getManifestNo())
        .append("|DT=")
        .append(DATE.format(now))
        .append("|HR=")
        .append(TIME.format(now))
        .append("|CARRIER=")
        .append(manifest.getCarrier().getName())
        .append("\n");

    sb.append("312|CUSTOMER|NAME=")
        .append(manifest.getCustomer().getName())
        .append("|REGION=")
        .append(manifest.getDestRegion().getCode())
        .append("\n");

    BigDecimal totalWeight = BigDecimal.ZERO;
    BigDecimal totalValue = BigDecimal.ZERO;
    for (ManifestInvoice item : items) {
      var invoice = item.getInvoice();
      sb.append("313|NFE|CHAVE=")
          .append(invoice.getNfKey())
          .append("|NF=")
          .append(invoice.getNfNumber())
          .append("|SERIE=")
          .append(invoice.getSeries())
          .append("|VALOR=")
          .append(formatMoney(invoice.getNetValue()))
          .append("|PESO=")
          .append(formatWeight(invoice.getWeightKg()))
          .append("|VOLUMES=")
          .append(invoice.getVolumeQty())
          .append("\n");
      totalWeight = totalWeight.add(invoice.getWeightKg());
      totalValue = totalValue.add(invoice.getNetValue());
    }

    sb.append("519|TRAILER|QTDE_NF=")
        .append(items.size())
        .append("|PESO_TOTAL=")
        .append(formatWeight(totalWeight))
        .append("|VALOR_TOTAL=")
        .append(formatMoney(totalValue));

    return sb.toString();
  }

  private String formatMoney(BigDecimal value) {
    if (value == null) {
      return "0.00";
    }
    return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
  }

  private String formatWeight(BigDecimal value) {
    if (value == null) {
      return "0.000";
    }
    return value.setScale(3, RoundingMode.HALF_UP).toPlainString();
  }
}
