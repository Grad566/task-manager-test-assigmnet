package test.task.app.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import test.task.app.dto.TaskParamDTO;
import test.task.app.model.Task;

/**
 * Класс для построения спецификаций запросов к сущности {@link Task}
 * Этот компонент предоставляет методы для создания спецификаций на основе
 * параметров, переданных через {@link TaskParamDTO}. Спецификации могут быть
 * использованы для фильтрации задач по различным критериям, таким как
 * автор, исполнитель, приоритет, статус и заголовок.
 */
@Component
public class TaskSpecification {
    /**
     * Строит спецификацию для фильтрации задач на основе переданных параметров.
     * @param params параметры для фильтрации задач
     * @return спецификация для фильтрации задач
     */
    public Specification<Task> build(TaskParamDTO params) {
        return withAssigneeId(params.getAssigneeId())
                .and(withAuthorId(params.getAuthorId()))
                .and(withPriority(params.getPriority()))
                .and(withStatus(params.getStatus()))
                .and(withTitleCont(params.getTitleCont()));
    }

    /**
     * Создает спецификацию для фильтрации задач по идентификатору автора.
     * @param authorId идентификатор автора
     * @return спецификация для фильтрации задач по автору
     */
    private Specification<Task> withAuthorId(Long authorId) {
        return (root, query, cb) -> authorId == null
                ? cb.conjunction()
                : cb.equal(root.get("author").get("id"), authorId);
    }

    /**
     * Создает спецификацию для фильтрации задач по идентификатору исполнителя.
     * @param assigneeId идентификатор исполнителя
     * @return спецификация для фильтрации задач по исполнителю
     */
    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null
                ? cb.conjunction()
                : cb.equal(root.get("assignees").get("id"), assigneeId);
    }

    /**
     * Создает спецификацию для фильтрации задач по части заголовка.
     * @param titleCont часть заголовка задачи
     * @return спецификация для фильтрации задач по заголовку
     */
    private Specification<Task> withTitleCont(String titleCont) {
        return (root, query, cb) -> titleCont == null
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("title")), "%" + titleCont.toLowerCase() + "%");
    }

    /**
     * Создает спецификацию для фильтрации задач по статусу.
     * @param status статус задачи
     * @return спецификация для фильтрации задач по статусу
     */
    private Specification<Task> withStatus(String status) {
        return (root, query, cb) -> status == null ? cb.conjunction()
                : cb.equal(root.get("status").get("name"), status);
    }

    /**
     * Создает спецификацию для фильтрации задач по приоритету.
     * @param priority приоритет задачи
     * @return спецификация для фильтрации задач по приоритету
     */
    private Specification<Task> withPriority(String priority) {
        return (root, query, cb) -> priority == null ? cb.conjunction()
                : cb.equal(root.get("priority").get("name"), priority);
    }
}
