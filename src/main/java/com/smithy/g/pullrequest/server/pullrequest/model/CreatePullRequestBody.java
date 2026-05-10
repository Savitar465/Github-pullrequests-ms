package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreatePullRequestBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CreatePullRequestBody {

  private String title;

  private String description;

  private String sourceBranch;

  private String targetBranch;

  public CreatePullRequestBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreatePullRequestBody(String title, String sourceBranch, String targetBranch) {
    this.title = title;
    this.sourceBranch = sourceBranch;
    this.targetBranch = targetBranch;
  }

  public CreatePullRequestBody title(String title) {
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

  public CreatePullRequestBody description(String description) {
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

  public CreatePullRequestBody sourceBranch(String sourceBranch) {
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

  public CreatePullRequestBody targetBranch(String targetBranch) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreatePullRequestBody createPullRequestBody = (CreatePullRequestBody) o;
    return Objects.equals(this.title, createPullRequestBody.title) &&
        Objects.equals(this.description, createPullRequestBody.description) &&
        Objects.equals(this.sourceBranch, createPullRequestBody.sourceBranch) &&
        Objects.equals(this.targetBranch, createPullRequestBody.targetBranch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, sourceBranch, targetBranch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreatePullRequestBody {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    sourceBranch: ").append(toIndentedString(sourceBranch)).append("\n");
    sb.append("    targetBranch: ").append(toIndentedString(targetBranch)).append("\n");
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

