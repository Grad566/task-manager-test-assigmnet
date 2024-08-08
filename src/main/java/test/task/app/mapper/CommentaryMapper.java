package test.task.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import test.task.app.dto.commentaryDTO.CommentaryCreatedDTO;
import test.task.app.dto.commentaryDTO.CommentaryDTO;
import test.task.app.dto.commentaryDTO.CommentaryUpdatedDTO;
import test.task.app.model.Commentary;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CommentaryMapper {
    @Mapping(target = "task", source = "taskId")
    public abstract Commentary map(CommentaryCreatedDTO data);
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "authorId", source = "author.id")
    public abstract CommentaryDTO map(Commentary model);
    public abstract void update(CommentaryUpdatedDTO data, @MappingTarget Commentary model);
}
