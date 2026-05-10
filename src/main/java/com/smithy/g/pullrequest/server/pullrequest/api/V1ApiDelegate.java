package com.smithy.g.pullrequest.server.pullrequest.api;

import com.smithy.g.pullrequest.server.pullrequest.model.BadRequestErrorResponseContent;
import java.math.BigDecimal;
import com.smithy.g.pullrequest.server.pullrequest.model.ConflictErrorResponseContent;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestCommentBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ForbiddenErrorResponseContent;
import com.smithy.g.pullrequest.server.pullrequest.model.InternalServerErrorResponseContent;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestCommentsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.NotFoundErrorResponseContent;
import com.smithy.g.pullrequest.server.pullrequest.model.PrStatus;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestCommentDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestMergeabilityDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewPullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.UnauthorizedErrorResponseContent;
import com.smithy.g.pullrequest.server.pullrequest.model.UnprocessableEntityErrorResponseContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link V1ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public interface V1ApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /v1/repos/{owner}/{repo}/pulls
     * Crea un pull request entre dos ramas. RF07.1
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param createPullRequestBody  (required)
     * @return CreatePullRequest 201 response (status code 201)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or ConflictError 409 response (status code 409)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#createPullRequest
     */
    default ResponseEntity<PullRequestDTO> createPullRequest(String owner,
        String repo,
        CreatePullRequestBody createPullRequestBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"repoId\" : \"repoId\", \"sourceBranch\" : \"sourceBranch\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"commitsCount\" : 6.027456183070403, \"mergedAt\" : \"mergedAt\", \"description\" : \"description\", \"title\" : \"title\", \"number\" : 0.8008281904610115, \"createdAt\" : \"createdAt\", \"targetBranch\" : \"targetBranch\", \"id\" : \"id\", \"hasConflicts\" : true, \"updatedAt\" : \"updatedAt\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /v1/repos/{owner}/{repo}/pulls/{prNumber}/comments
     * Agrega un comentario general o por linea a un pull request.
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param prNumber  (required)
     * @param createPullRequestCommentBody  (required)
     * @return CreatePullRequestComment 201 response (status code 201)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#createPullRequestComment
     */
    default ResponseEntity<PullRequestCommentDTO> createPullRequestComment(String owner,
        String repo,
        BigDecimal prNumber,
        CreatePullRequestCommentBody createPullRequestCommentBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"createdAt\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"filePath\" : \"filePath\", \"id\" : \"id\", \"pullRequestId\" : \"pullRequestId\", \"body\" : \"body\", \"lineNumber\" : 1.0800828190461012, \"updatedAt\" : \"updatedAt\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/pulls/{prNumber}
     * Obtiene el detalle de un pull request.
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param prNumber  (required)
     * @return GetPullRequest 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getPullRequest
     */
    default ResponseEntity<PullRequestDTO> getPullRequest(String owner,
        String repo,
        BigDecimal prNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"repoId\" : \"repoId\", \"sourceBranch\" : \"sourceBranch\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"commitsCount\" : 6.027456183070403, \"mergedAt\" : \"mergedAt\", \"description\" : \"description\", \"title\" : \"title\", \"number\" : 0.8008281904610115, \"createdAt\" : \"createdAt\", \"targetBranch\" : \"targetBranch\", \"id\" : \"id\", \"hasConflicts\" : true, \"updatedAt\" : \"updatedAt\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/pulls/{prNumber}/mergeability
     * Evalua conflictos y mergeabilidad antes de merge. RF07.3
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param prNumber  (required)
     * @return GetPullRequestMergeability 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#getPullRequestMergeability
     */
    default ResponseEntity<PullRequestMergeabilityDTO> getPullRequestMergeability(String owner,
        String repo,
        BigDecimal prNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"reason\" : \"reason\", \"mergeable\" : true, \"prNumber\" : 0.8008281904610115, \"hasConflicts\" : true }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/pulls/{prNumber}/comments
     * Lista comentarios de un pull request.
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param prNumber  (required)
     * @return ListPullRequestComments 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#listPullRequestComments
     */
    default ResponseEntity<ListPullRequestCommentsBody> listPullRequestComments(String owner,
        String repo,
        BigDecimal prNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"comments\" : [ { \"createdAt\" : \"createdAt\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"filePath\" : \"filePath\", \"id\" : \"id\", \"pullRequestId\" : \"pullRequestId\", \"body\" : \"body\", \"lineNumber\" : 1.0800828190461012, \"updatedAt\" : \"updatedAt\" }, { \"createdAt\" : \"createdAt\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"filePath\" : \"filePath\", \"id\" : \"id\", \"pullRequestId\" : \"pullRequestId\", \"body\" : \"body\", \"lineNumber\" : 1.0800828190461012, \"updatedAt\" : \"updatedAt\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /v1/repos/{owner}/{repo}/pulls
     * Lista los pull requests del repositorio. RF07
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param status  (optional)
     * @return ListPullRequests 200 response (status code 200)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#listPullRequests
     */
    default ResponseEntity<ListPullRequestsBody> listPullRequests(String owner,
        String repo,
        PrStatus status) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"pullRequests\" : [ { \"repoId\" : \"repoId\", \"sourceBranch\" : \"sourceBranch\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"commitsCount\" : 6.027456183070403, \"mergedAt\" : \"mergedAt\", \"description\" : \"description\", \"title\" : \"title\", \"number\" : 0.8008281904610115, \"createdAt\" : \"createdAt\", \"targetBranch\" : \"targetBranch\", \"id\" : \"id\", \"hasConflicts\" : true, \"updatedAt\" : \"updatedAt\" }, { \"repoId\" : \"repoId\", \"sourceBranch\" : \"sourceBranch\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"commitsCount\" : 6.027456183070403, \"mergedAt\" : \"mergedAt\", \"description\" : \"description\", \"title\" : \"title\", \"number\" : 0.8008281904610115, \"createdAt\" : \"createdAt\", \"targetBranch\" : \"targetBranch\", \"id\" : \"id\", \"hasConflicts\" : true, \"updatedAt\" : \"updatedAt\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /v1/repos/{owner}/{repo}/pulls/{prNumber}/merge
     * Ejecuta merge de un pull request aprobado y sin conflictos.
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param prNumber  (required)
     * @param mergePullRequestBody  (required)
     * @return MergePullRequest 200 response (status code 200)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or ConflictError 409 response (status code 409)
     *         or UnprocessableEntityError 422 response (status code 422)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#mergePullRequest
     */
    default ResponseEntity<PullRequestDTO> mergePullRequest(String owner,
        String repo,
        BigDecimal prNumber,
        MergePullRequestBody mergePullRequestBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"repoId\" : \"repoId\", \"sourceBranch\" : \"sourceBranch\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"commitsCount\" : 6.027456183070403, \"mergedAt\" : \"mergedAt\", \"description\" : \"description\", \"title\" : \"title\", \"number\" : 0.8008281904610115, \"createdAt\" : \"createdAt\", \"targetBranch\" : \"targetBranch\", \"id\" : \"id\", \"hasConflicts\" : true, \"updatedAt\" : \"updatedAt\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PATCH /v1/repos/{owner}/{repo}/pulls/{prNumber}/review
     * Aprueba o solicita cambios en un pull request. RF07.2
     *
     * @param owner  (required)
     * @param repo  (required)
     * @param prNumber  (required)
     * @param reviewPullRequestBody  (required)
     * @return ReviewPullRequest 200 response (status code 200)
     *         or BadRequestError 400 response (status code 400)
     *         or UnauthorizedError 401 response (status code 401)
     *         or ForbiddenError 403 response (status code 403)
     *         or NotFoundError 404 response (status code 404)
     *         or InternalServerError 500 response (status code 500)
     * @see V1Api#reviewPullRequest
     */
    default ResponseEntity<PullRequestDTO> reviewPullRequest(String owner,
        String repo,
        BigDecimal prNumber,
        ReviewPullRequestBody reviewPullRequestBody) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"repoId\" : \"repoId\", \"sourceBranch\" : \"sourceBranch\", \"author\" : { \"id\" : \"id\", \"username\" : \"username\" }, \"commitsCount\" : 6.027456183070403, \"mergedAt\" : \"mergedAt\", \"description\" : \"description\", \"title\" : \"title\", \"number\" : 0.8008281904610115, \"createdAt\" : \"createdAt\", \"targetBranch\" : \"targetBranch\", \"id\" : \"id\", \"hasConflicts\" : true, \"updatedAt\" : \"updatedAt\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"message\" : \"message\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
