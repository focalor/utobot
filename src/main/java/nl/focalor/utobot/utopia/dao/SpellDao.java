package nl.focalor.utobot.utopia.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import nl.focalor.utobot.utopia.model.SpellCast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SpellDao implements ISpellDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SpellDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void createCast(SpellCast spellCast) {
		PreparedStatementCreator psc = con -> {
			PreparedStatement statement = con.prepareStatement(
					"INSERT INTO spells(spellId) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, spellCast.getSpell().getId());
			return statement;
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		spellCast.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(long id) {
		// TODO
	}

	@Override
	public void deleteCast(long id) {
		// TODO
	}
}
