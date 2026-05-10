$version: "2"

namespace com.githubx.pullrequest

use aws.protocols#restJson1

@title("GitHub Pull Request API")
@restJson1
@httpBearerAuth
@documentation("Servicio dedicado para pull requests y comentarios de pull requests.")
service PullRequestApi {
    version: "1.0.0"
    operations: [
        ListPullRequests
        CreatePullRequest
        GetPullRequest
        ReviewPullRequest
        MergePullRequest
        ListPullRequestComments
        CreatePullRequestComment
        GetPullRequestMergeability
    ]
}
