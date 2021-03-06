package cz.muni.fi.pa165.mm.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.pa165.mm.api.LocalDateDeserializer;
import cz.muni.fi.pa165.mm.api.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Václav Stehlík; 487580
 */
public class AlbumCreateDTO {
    @NotNull
    @Size(min = 2, max = 50, message = "Name length must be between 2 to 50 characters.")
    private String name;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull
    private Long performerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getPerformerId() {
        return performerId;
    }

    public void setPerformerId(Long performerId) {
        this.performerId = performerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof AlbumCreateDTO)) {
            return false;
        }

        AlbumCreateDTO a = (AlbumCreateDTO) o;
        return this.getName().equals(a.getName()) && this.getReleaseDate().equals(a.getReleaseDate()) && this.getPerformerId().equals(a.getPerformerId());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
        result = prime * result + ((performerId == null) ? 0 : performerId.hashCode());
        return result;
    }
}
