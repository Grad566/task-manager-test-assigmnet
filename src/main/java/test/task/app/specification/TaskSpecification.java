package test.task.app.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import test.task.app.dto.TaskParamDTO;
import test.task.app.model.Task;

@Component
public class TaskSpecification {
    public Specification<Task> build(TaskParamDTO params) {
        return withAssigneeId(params.getAssigneeId())
                .and(withAuthorId(params.getAuthorId()))
                .and(withPriority(params.getPriority()))
                .and(withStatus(params.getStatus()))
                .and(withTitleCont(params.getTitleCont()));
    }

    private Specification<Task> withAuthorId(Long authorId) {
        return (root, query, cb) -> authorId == null
                ? cb.conjunction()
                : cb.equal(root.get("author").get("id"), authorId);
    }

    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null
                ? cb.conjunction()
                : cb.equal(root.get("assignees").get("id"), assigneeId);
    }

    private Specification<Task> withTitleCont(String titleCont) {
        return (root, query, cb) -> titleCont == null
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("title")), "%" + titleCont.toLowerCase() + "%");
    }

    private Specification<Task> withStatus(String status) {
        return (root, query, cb) -> status == null ? cb.conjunction()
                : cb.equal(root.get("status").get("name"), status);
    }

    private Specification<Task> withPriority(String priority) {
        return (root, query, cb) -> priority == null ? cb.conjunction()
                : cb.equal(root.get("priority").get("name"), priority);
    }
}
