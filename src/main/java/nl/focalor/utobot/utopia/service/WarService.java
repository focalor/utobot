package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.model.repository.WarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        War openWar = warDao.findOpenWar();
        if(openWar != null){
            openWar.setEndDate(new Date());
            warDao.save(openWar);
        }
    }

    @Override
    public War getCurrentWar() {
        return warDao.findOpenWar();
    }
}
