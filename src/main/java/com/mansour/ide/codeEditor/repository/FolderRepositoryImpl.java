package com.mansour.ide.codeEditor.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FolderRepositoryImpl implements FolderRepository{
    /*
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Folder> folderRowMapper() {
        return ((rs, rowNum) -> {
            Folder folder= new Folder();
            folder.setId(rs.getLong("id"));
            folder.setName(rs.getString("name"));

            Long parentFolderId = rs.getLong("parentFolderId");
            if (!rs.wasNull()) {
                folder.setParentFolderId(new AggregateReference<>() {
                    @Override
                    public Long getId() {
                        return parentFolderId;
                    }
                });
            }

            Set<File> files = new HashSet<>();
            do {
                File file = new File(
                        rs.getLong("file_id"),
                        rs.getString("file_name"),
                        rs.getString("file_content"),
                        (LocalDateTime) rs.getObject("file_createdt"),
                        (LocalDateTime) rs.getObject("file_updatedt")
                );

                files.add(file);
            } while(rs.next());

            return folder;
        });
    }

    public Folder create(){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("codeEditor").usingGeneratedKeyColumns("id");

    }

    @Override
    public Folder save(Folder folderNode) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("folder").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", folderNode.getName());
        params.put("parentFolder", folderNode.getParentFolderId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        folderNode.setId(key.longValue());

        return folderNode;
    }

    @Override
    public List<Folder> getAllFolder() {
        return jdbcTemplate.query("SELECT * FROM folder", folderRowMapper());
    }

    @Override
    public Optional<Folder> getFolderByName(String name) {
        List<Folder> result = jdbcTemplate.query("SELECT * FROM folder WHERE name = ?", folderRowMapper(), name);
        return result.stream().findAny();
    }
     */
}
