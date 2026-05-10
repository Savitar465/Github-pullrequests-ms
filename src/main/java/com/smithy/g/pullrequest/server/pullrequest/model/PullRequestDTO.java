package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.smithy.g.pullrequest.server.pullrequest.model.AuthorSummary;
import com.smithy.g.pullrequest.server.pullrequest.model.PrStatus;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PullRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class PullRequestDTO {

  private String id;

  private String repoId;

  private BigDecimal number;

  private String title;

  private String description;

  private String sourceBranch;

  private String targetBranch;

  private AuthorSummary author;

  private PrStatus status;

  private Boolean hasConflicts;

  private BigDecimal commitsCount;

  private String createdAt;

  private String updatedAt;

  private String mergedAt;

  public PullRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PullRequestDTO(String id, String repoId, BigDecimal number, String title, String sourceBranch, String targetBranch, AuthorSummary author, PrStatus status, Boolean hasConflicts, BigDecimal commitsCount, String createdAt, String updatedAt) {
    this.id = id;
    this.repoId = repoId;
    this.number = number;
    this.title = title;
    this.sourceBranch = sourceBranch;
    this.targetBranch = targetBranch;
    this.author = author;
    this.status = status;
    this.hasConflicts = hasConflicts;
    this.commitsCount = commitsCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public PullRequestDTO id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$") 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PullRequestDTO repoId(String repoId) {
    this.repoId = repoId;
    return this;
  }

  /**
   * Get repoId
   * @return repoId
  */
  @NotNull 
  @Schema(name = "repoId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("repoId")
  public String getRepoId() {
    return repoId;
  }

  public void setRepoId(String repoId) {
    this.repoId = repoId;
  }

  public PullRequestDTO number(BigDecimal number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * @return number
  */
  @NotNull @Valid 
  @Schema(name = "number", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("number")
  public BigDecimal getNumber() {
    return number;
  }

  public void setNumber(BigDecimal number) {
    this.number = number;
  }

  public PullRequestDTO title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @NotNull @Size(min = 1, max = 255) 
  @Schema(name = "title", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PullRequestDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PullRequestDTO sourceBranch(String sourceBranch) {
    this.sourceBranch = sourceBranch;
    return this;
  }

  /**
   * Get sourceBranch
   * @return sourceBranch
  */
  @NotNull 
  @Schema(name = "sourceBranch", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sourceBranch")
  public String getSourceBranch() {
    return sourceBranch;
  }

  public void setSourceBranch(String sourceBranch) {
    this.sourceBranch = sourceBranch;
  }

  public PullRequestDTO targetBranch(String targetBranch) {
    this.targetBranch = targetBranch;
    return this;
  }

  /**
   * Get targetBranch
   * @return targetBranch
  */
  @NotNull 
  @Schema(name = "targetBranch", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("targetBranch")
  public String getTargetBranch() {
    return targetBranch;
  }

  public void setTargetBranch(String targetBranch) {
    this.targetBranch = targetBranch;
  }

  public PullRequestDTO author(AuthorSummary author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  @NotNull @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("author")
  public AuthorSummary getAuthor() {
    return author;
  }

  public void setAuthor(AuthorSummary author) {
    this.author = author;
  }

  public PullRequestDTO status(PrStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public PrStatus getStatus() {
    return status;
  }

  public void setStatus(PrStatus status) {
    this.status = status;
  }

  public PullRequestDTO hasConflicts(Boolean hasConflicts) {
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

  public PullRequestDTO commitsCount(BigDecimal commitsCount) {
    this.commitsCount = commitsCount;
    return this;
  }

  /**
   * Get commitsCount
   * @return commitsCount
  */
  @NotNull @Valid 
  @Schema(name = "commitsCount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("commitsCount")
  public BigDecimal getCommitsCount() {
    return commitsCount;
  }

  public void setCommitsCount(BigDecimal commitsCount) {
    this.commitsCount = commitsCount;
  }

  public PullRequestDTO createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @NotNull 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public PullRequestDTO updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
  */
  @NotNull 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public PullRequestDTO mergedAt(String mergedAt) {
    this.mergedAt = mergedAt;
    return this;
  }

  /**
   * Get mergedAt
   * @return mergedAt
  */
  
  @Schema(name = "mergedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mergedAt")
  public String getMergedAt() {
    return mergedAt;
  }

  public void setMergedAt(String mergedAt) {
    this.mergedAt = mergedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PullRequestDTO pullRequestDTO = (PullRequestDTO) o;
    return Objects.equals(this.id, pullRequestDTO.id) &&
        Objects.equals(this.repoId, pullRequestDTO.repoId) &&
        Objects.equals(this.number, pullRequestDTO.number) &&
        Objects.equals(this.title, pullRequestDTO.title) &&
        Objects.equals(this.description, pullRequestDTO.description) &&
        Objects.equals(this.sourceBranch, pullRequestDTO.sourceBranch) &&
        Objects.equals(this.targetBranch, pullRequestDTO.targetBranch) &&
        Objects.equals(this.author, pullRequestDTO.author) &&
        Objects.equals(this.status, pullRequestDTO.status) &&
        Objects.equals(this.hasConflicts, pullRequestDTO.hasConflicts) &&
        Objects.equals(this.commitsCount, pullRequestDTO.commitsCount) &&
        Objects.equals(this.createdAt, pullRequestDTO.createdAt) &&
        Objects.equals(this.updatedAt, pullRequestDTO.updatedAt) &&
        Objects.equals(this.mergedAt, pullRequestDTO.mergedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, repoId, number, title, description, sourceBranch, targetBranch, author, status, hasConflicts, commitsCount, createdAt, updatedAt, mergedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PullRequestDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    repoId: ").append(toIndentedString(repoId)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    sourceBranch: ").append(toIndentedString(sourceBranch)).append("\n");
    sb.append("    targetBranch: ").append(toIndentedString(targetBranch)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    hasConflicts: ").append(toIndentedString(hasConflicts)).append("\n");
    sb.append("    commitsCount: ").append(toIndentedString(commitsCount)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    mergedAt: ").append(toIndentedString(mergedAt)).append("\n");
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

