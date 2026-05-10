package com.smithy.g.pullrequest.server.pullrequest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestDTO;
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
 * ListPullRequestsBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class ListPullRequestsBody {

  @Valid
  private List<@Valid PullRequestDTO> pullRequests = new ArrayList<>();

  public ListPullRequestsBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ListPullRequestsBody(List<@Valid PullRequestDTO> pullRequests) {
    this.pullRequests = pullRequests;
  }

  public ListPullRequestsBody pullRequests(List<@Valid PullRequestDTO> pullRequests) {
    this.pullRequests = pullRequests;
    return this;
  }

  public ListPullRequestsBody addPullRequestsItem(PullRequestDTO pullRequestsItem) {
    if (this.pullRequests == null) {
      this.pullRequests = new ArrayList<>();
    }
    this.pullRequests.add(pullRequestsItem);
    return this;
  }

  /**
   * Get pullRequests
   * @return pullRequests
  */
  @NotNull @Valid 
  @Schema(name = "pullRequests", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pullRequests")
  public List<@Valid PullRequestDTO> getPullRequests() {
    return pullRequests;
  }

  public void setPullRequests(List<@Valid PullRequestDTO> pullRequests) {
    this.pullRequests = pullRequests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListPullRequestsBody listPullRequestsBody = (ListPullRequestsBody) o;
    return Objects.equals(this.pullRequests, listPullRequestsBody.pullRequests);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pullRequests);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListPullRequestsBody {\n");
    sb.append("    pullRequests: ").append(toIndentedString(pullRequests)).append("\n");
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

