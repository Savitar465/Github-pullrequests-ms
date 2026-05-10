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
 * CreatePullRequestCommentBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CreatePullRequestCommentBody {

  private String body;

  private String filePath;

  private BigDecimal lineNumber;

  public CreatePullRequestCommentBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreatePullRequestCommentBody(String body) {
    this.body = body;
  }

  public CreatePullRequestCommentBody body(String body) {
    this.body = body;
    return this;
  }

  /**
   * Get body
   * @return body
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "body", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("body")
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public CreatePullRequestCommentBody filePath(String filePath) {
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

  public CreatePullRequestCommentBody lineNumber(BigDecimal lineNumber) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreatePullRequestCommentBody createPullRequestCommentBody = (CreatePullRequestCommentBody) o;
    return Objects.equals(this.body, createPullRequestCommentBody.body) &&
        Objects.equals(this.filePath, createPullRequestCommentBody.filePath) &&
        Objects.equals(this.lineNumber, createPullRequestCommentBody.lineNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(body, filePath, lineNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreatePullRequestCommentBody {\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    filePath: ").append(toIndentedString(filePath)).append("\n");
    sb.append("    lineNumber: ").append(toIndentedString(lineNumber)).append("\n");
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

