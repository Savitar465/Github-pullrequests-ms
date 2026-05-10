package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestCommentDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ListPullRequestCommentsBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class ListPullRequestCommentsBody {

  @Valid
  private List<@Valid PullRequestCommentDTO> comments = new ArrayList<>();

  public ListPullRequestCommentsBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ListPullRequestCommentsBody(List<@Valid PullRequestCommentDTO> comments) {
    this.comments = comments;
  }

  public ListPullRequestCommentsBody comments(List<@Valid PullRequestCommentDTO> comments) {
    this.comments = comments;
    return this;
  }

  public ListPullRequestCommentsBody addCommentsItem(PullRequestCommentDTO commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Get comments
   * @return comments
  */
  @NotNull @Valid 
  @Schema(name = "comments", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("comments")
  public List<@Valid PullRequestCommentDTO> getComments() {
    return comments;
  }

  public void setComments(List<@Valid PullRequestCommentDTO> comments) {
    this.comments = comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListPullRequestCommentsBody listPullRequestCommentsBody = (ListPullRequestCommentsBody) o;
    return Objects.equals(this.comments, listPullRequestCommentsBody.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListPullRequestCommentsBody {\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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

