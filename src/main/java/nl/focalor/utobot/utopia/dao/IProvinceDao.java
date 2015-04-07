package nl.focalor.utobot.utopia.dao;

import java.util.List;
import nl.focalor.utobot.utopia.model.Province;

public interface IProvinceDao {
	public void create(Province province);

	public Province get(long id);

	public List<Province> find(Long personId, String namePart, Boolean fuzzy);
}
