package ch.hallo02.assessments.bank.smoothie.persistence;

import ch.hallo02.assessments.bank.smoothie.model.Smoothie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class SmoothiePersistence {

    private final JdbcTemplate jdbcTemplate;

    public SmoothiePersistence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Smoothie> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM SMOOTHIES", getRowMapper());
    }

    public Optional<Smoothie> getById(String id) {
        try {
            return Optional.ofNullable(
                    this.jdbcTemplate.query(connection -> {
                                PreparedStatement ps = connection.prepareStatement("SELECT * FROM SMOOTHIES s WHERE s.id = ?");
                                ps.setString(1, id);
                                return ps;
                            },
                            getResultSetExtractor(true))
            );
        } catch (Exception e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    public void update(Smoothie smoothie) {

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    """
                            UPDATE SMOOTHIES 
                            SET name = ?, 
                            slogan = ?, 
                            carbohydrates = ?,
                            fat = ?,
                            protein = ?
                            WHERE id = ?
                            """
            );
            ps.setString(1, smoothie.name());
            ps.setString(2, smoothie.slogan());
            ps.setString(3, smoothie.carbohydrates());
            ps.setString(4, smoothie.fat());
            ps.setString(5, smoothie.protein());
            ps.setString(6, smoothie.id());
            return ps;
        });
    }

    public Smoothie add(Smoothie smoothie) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            """
                                    INSERT 
                                    INTO SMOOTHIES 
                                    (name, slogan, carbohydrates, fat, protein)
                                    VALUES(?,?,?,?,?)
                                    """,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, smoothie.name());
                    ps.setString(2, smoothie.slogan());
                    ps.setString(3, smoothie.carbohydrates());
                    ps.setString(4, smoothie.fat());
                    ps.setString(5, smoothie.protein());
                    return ps;
                },
                keyHolder
        );

        return this.getById(keyHolder.getKey().toString()).orElseThrow();
    }

    public void delete(Smoothie smoothie) {

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    """
                            DELETE 
                            FROM SMOOTHIES 
                            WHERE id = ?
                            """);
            ps.setString(1, smoothie.id());
            return ps;
        });
    }

    private static RowMapper<Smoothie> getRowMapper() {

        return (ResultSet rs, int rowNum) -> getResultSetExtractor(false).extractData(rs);
    }

    private static ResultSetExtractor<Smoothie> getResultSetExtractor(boolean onNextCall) {

        return (ResultSet rs) -> {
            if (onNextCall) {
                rs.next();
            }
            return new Smoothie(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("slogan"),
                    rs.getString("carbohydrates"),
                    rs.getString("fat"),
                    rs.getString("protein")
            );
        };
    }
}

