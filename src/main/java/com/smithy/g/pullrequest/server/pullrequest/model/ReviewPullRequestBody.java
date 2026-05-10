package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReviewPullRequestBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class ReviewPullRequestBody {

  private ReviewDecision decision;

  private String comment;

  public ReviewPullRequestBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ReviewPullRequestBody(ReviewDecision decision) {
    this.decision = decision;
  }

  public ReviewPullRequestBody decision(ReviewDecision decision) {
    this.decision = decision;
    return this;
  }

  /**
   * Get decision
   * @return decision
  */
  @NotNull @Valid 
  @Schema(name = "decision", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("decision")
  public ReviewDecision getDecision() {
    return decision;
  }

  public void setDecision(ReviewDecision decision) {
    this.decision = decision;
  }

  public ReviewPullRequestBody comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
  */
  
  @Schema(name = "comment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("comment")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReviewPullRequestBody reviewPullRequestBody = (ReviewPullRequestBody) o;
    return Objects.equals(this.decision, reviewPullRequestBody.decision) &&
        Objects.equals(this.comment, reviewPullRequestBody.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(decision, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReviewPullRequestBody {\n");
    sb.append("    decision: ").append(toIndentedString(decision)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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

