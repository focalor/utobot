package nl.focalor.utobot.utopia.service;

import java.util.Date;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.model.repository.WarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luigibanzato on 12/04/2015.
 */
@Service
public class WarService implements IWarService {

	@Autowired
	private WarRepository warDao;

	@Override
	public War startWar() {
		endWar();

		War newWar = new War();
		newWar.setStartDate(new Date());
		newWar = warDao.save(newWar);
		return newWar;
	}

	@Override
	public void endWar() {
		War openWar = warDao.findByEndDateNull();
		if (openWar != null) {
			openWar.setEndDate(new Date());
			warDao.save(openWar);
		}
	}

	@Override
	public War getCurrentWar() {
		return warDao.findByEndDateNull();
	}

	@Override
	public void addWar(War war) {
		warDao.save(war);
	}

	@Override
	public void removeWar(War war) {
		warDao.delete(war);
	}

	@Override
	public void findWar(Long id) {
		warDao.findOne(id);
	}

	@Override
	public void updateDate(War war) {
		warDao.save(war);
	}
}
