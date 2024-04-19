package com.mansour.ide.codeEditor.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "FOLDER")
public class Folder {
    /*
    @Id
    @Column("folder_id")
    private Long id;
    private String name;
    private AggregateReference<Folder, Long> parentFolderId;

    @MappedCollection(idColumn = "file_id")
    private Set<File> files = new HashSet<>();

    public void addFile(File file){
        files.add(file);
    }
     */
}
