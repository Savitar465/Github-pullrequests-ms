package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.pullrequest.server.pullrequest.model.AuthorSummary;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PullRequestCommentDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class PullRequestCommentDTO {

  private String id;

  private String pullRequestId;

  private String body;

  private String filePath;

  private BigDecimal lineNumber;

  private AuthorSummary author;

  private String createdAt;

  private String updatedAt;

  public PullRequestCommentDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PullRequestCommentDTO(String id, String pullRequestId, String body, AuthorSummary author, String createdAt, String updatedAt) {
    this.id = id;
    this.pullRequestId = pullRequestId;
    this.body = body;
    this.author = author;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public PullRequestCommentDTO id(String id) {
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

  public PullRequestCommentDTO pullRequestId(String pullRequestId) {
    this.pullRequestId = pullRequestId;
    return this;
  }

  /**
   * Get pullRequestId
   * @return pullRequestId
  */
  @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$") 
  @Schema(name = "pullRequestId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pullRequestId")
  public String getPullRequestId() {
    return pullRequestId;
  }

  public void setPullRequestId(String pullRequestId) {
    this.pullRequestId = pullRequestId;
  }

  public PullRequestCommentDTO body(String body) {
    this.body = body;
    return this;
  }

  /**
   * Get body
   * @return body
  */
  @NotNull 
  @Schema(name = "body", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("body")
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public PullRequestCommentDTO filePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  /**
   * Get filePath
   * @return filePath
  */
  
  @Schema(name = "filePath", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filePath")
  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public PullRequestCommentDTO lineNumber(BigDecimal lineNumber) {
    this.lineNumber = lineNumber;
    return this;
  }

  /**
   * Get lineNumber
   * minimum: 1
   * @return lineNumber
  */
  @Valid @DecimalMin("1") 
  @Schema(name = "lineNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lineNumber")
  public BigDecimal getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(BigDecimal lineNumber) {
    this.lineNumber = lineNumber;
  }

  public PullRequestCommentDTO author(AuthorSummary author) {
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

  public PullRequestCommentDTO createdAt(String createdAt) {
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

  public PullRequestCommentDTO updatedAt(String updatedAt) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PullRequestCommentDTO pullRequestCommentDTO = (PullRequestCommentDTO) o;
    return Objects.equals(this.id, pullRequestCommentDTO.id) &&
        Objects.equals(this.pullRequestId, pullRequestCommentDTO.pullRequestId) &&
        Objects.equals(this.body, pullRequestCommentDTO.body) &&
        Objects.equals(this.filePath, pullRequestCommentDTO.filePath) &&
        Objects.equals(this.lineNumber, pullRequestCommentDTO.lineNumber) &&
        Objects.equals(this.author, pullRequestCommentDTO.author) &&
        Objects.equals(this.createdAt, pullRequestCommentDTO.createdAt) &&
        Objects.equals(this.updatedAt, pullRequestCommentDTO.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, pullRequestId, body, filePath, lineNumber, author, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PullRequestCommentDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    pullRequestId: ").append(toIndentedString(pullRequestId)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    filePath: ").append(toIndentedString(filePath)).append("\n");
    sb.append("    lineNumber: ").append(toIndentedString(lineNumber)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

