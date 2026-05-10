package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PullRequestMergeabilityDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class PullRequestMergeabilityDTO {

  private BigDecimal prNumber;

  private Boolean mergeable;

  private Boolean hasConflicts;

  private String reason;

  public PullRequestMergeabilityDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PullRequestMergeabilityDTO(BigDecimal prNumber, Boolean mergeable, Boolean hasConflicts) {
    this.prNumber = prNumber;
    this.mergeable = mergeable;
    this.hasConflicts = hasConflicts;
  }

  public PullRequestMergeabilityDTO prNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
    return this;
  }

  /**
   * Get prNumber
   * @return prNumber
  */
  @NotNull @Valid 
  @Schema(name = "prNumber", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("prNumber")
  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public PullRequestMergeabilityDTO mergeable(Boolean mergeable) {
    this.mergeable = mergeable;
    return this;
  }

  /**
   * Get mergeable
   * @return mergeable
  */
  @NotNull 
  @Schema(name = "mergeable", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mergeable")
  public Boolean getMergeable() {
    return mergeable;
  }

  public void setMergeable(Boolean mergeable) {
    this.mergeable = mergeable;
  }

  public PullRequestMergeabilityDTO hasConflicts(Boolean hasConflicts) {
    this.hasConflicts = hasConflicts;
    return this;
  }

  /**
   * Get hasConflicts
   * @return hasConflicts
  */
  @NotNull 
  @Schema(name = "hasConflicts", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hasConflicts")
  public Boolean getHasConflicts() {
    return hasConflicts;
  }

  public void setHasConflicts(Boolean hasConflicts) {
    this.hasConflicts = hasConflicts;
  }

  public PullRequestMergeabilityDTO reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Get reason
   * @return reason
  */
  
  @Schema(name = "reason", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("reason")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PullRequestMergeabilityDTO pullRequestMergeabilityDTO = (PullRequestMergeabilityDTO) o;
    return Objects.equals(this.prNumber, pullRequestMergeabilityDTO.prNumber) &&
        Objects.equals(this.mergeable, pullRequestMergeabilityDTO.mergeable) &&
        Objects.equals(this.hasConflicts, pullRequestMergeabilityDTO.hasConflicts) &&
        Objects.equals(this.reason, pullRequestMergeabilityDTO.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prNumber, mergeable, hasConflicts, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PullRequestMergeabilityDTO {\n");
    sb.append("    prNumber: ").append(toIndentedString(prNumber)).append("\n");
    sb.append("    mergeable: ").append(toIndentedString(mergeable)).append("\n");
    sb.append("    hasConflicts: ").append(toIndentedString(hasConflicts)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

