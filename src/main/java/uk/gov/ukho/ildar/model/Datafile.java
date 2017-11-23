package uk.gov.ukho.ildar.model;
import com.yahoo.elide.annotation.Include;

import javax.persistence.*;



@Entity
@Table(name = "datafiles")
//@Include(rootLevel = true)
//@SharePermission(expression = "Prefab.Role.All")
//@Include(rootLevel = true)
public class Datafile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;

    public Datafile(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public Datafile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}