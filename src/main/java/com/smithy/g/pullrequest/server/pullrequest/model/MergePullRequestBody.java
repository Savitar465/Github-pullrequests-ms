package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MergePullRequestBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class MergePullRequestBody {

  private MergeStrategy strategy;

  private String commitMessage;

  public MergePullRequestBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MergePullRequestBody(MergeStrategy strategy) {
    this.strategy = strategy;
  }

  public MergePullRequestBody strategy(MergeStrategy strategy) {
    this.strategy = strategy;
    return this;
  }

  /**
   * Get strategy
   * @return strategy
  */
  @NotNull @Valid 
  @Schema(name = "strategy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("strategy")
  public MergeStrategy getStrategy() {
    return strategy;
  }

  public void setStrategy(MergeStrategy strategy) {
    this.strategy = strategy;
  }

  public MergePullRequestBody commitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
    return this;
  }

  /**
   * Get commitMessage
   * @return commitMessage
  */
  @Size(max = 500) 
  @Schema(name = "commitMessage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commitMessage")
  public String getCommitMessage() {
    return commitMessage;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MergePullRequestBody mergePullRequestBody = (MergePullRequestBody) o;
    return Objects.equals(this.strategy, mergePullRequestBody.strategy) &&
        Objects.equals(this.commitMessage, mergePullRequestBody.commitMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(strategy, commitMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MergePullRequestBody {\n");
    sb.append("    strategy: ").append(toIndentedString(strategy)).append("\n");
    sb.append("    commitMessage: ").append(toIndentedString(commitMessage)).append("\n");
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

