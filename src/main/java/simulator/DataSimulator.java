package simulator;

import model.entity.PlantInstance;
import model.entity.Sensor;
import model.entity.Telemetry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataSimulator {
    private final Random random = new Random();

    //last values for smooth changes
    private final Map<Long, DeviceState> deviceStates = new ConcurrentHashMap<>();

    public Telemetry generateTelemetry(PlantInstance plant, Sensor sensor) {
        DeviceState state = deviceStates.computeIfAbsent(
                plant.getId(),
                k -> new DeviceState()
        );

        Telemetry telemetry = new Telemetry();
        telemetry.setPlant(plant);
        telemetry.setSensor(sensor);
        telemetry.setTimestamp(LocalDateTime.now());
        telemetry.setSource("SIMULATOR");

        //all values on every telemetry record
        telemetry.setTemp(generateTemperature(plant, state));
        telemetry.setHumidity(generateHumidityAir(plant, state));
        telemetry.setSoilMoisture(generateSoilMoisture(plant, state));
        telemetry.setLight(generateLight(plant, state));

        return telemetry;
    }

    private BigDecimal generateTemperature(PlantInstance plant, DeviceState state) {
        BigDecimal prevTemp = state.getLastTemperature();
        BigDecimal min = BigDecimal.valueOf(10);
        BigDecimal max = getEffectiveTempMax(plant);

        //LocalTime now = LocalTime.now();
        //double timeFactor = Math.sin((now.getHour() - 6) * Math.PI / 12);
        BigDecimal newTemp = min.add(max.subtract(min).multiply(BigDecimal.valueOf(0.5)));

        if (prevTemp != null) {
            newTemp = prevTemp.add(newTemp.subtract(prevTemp).multiply(BigDecimal.valueOf(0.3)));
        }
        if (state.heatingActive) {
            System.out.println("Heating boost applied, plant=" + plant.getId() + ", timer left=" + state.heatingTimer);

            newTemp = newTemp.add(BigDecimal.valueOf(2));
            state.decHeatingTimer();
        }

        state.setLastTemperature(newTemp);
        return newTemp.setScale(1, RoundingMode.HALF_UP);
    }

    private Integer generateHumidityAir(PlantInstance plant, DeviceState state) {
        Integer prevHum = state.getLastHumidityAir();
        int min = 30;
        int max = 80;

        int base = prevHum != null ? prevHum : (min + max) / 2;
        int delta = random.nextInt(20) - 10;
        int newHum = base + delta;

        if (state.humActive) {
            newHum = Math.min(newHum + 3, max);
            state.decHumTimer();
        }
        newHum = Math.max(min, Math.min(max, base + delta));

        state.setLastHumidityAir(newHum);
        return newHum;
    }

    private Integer generateSoilMoisture(PlantInstance plant, DeviceState state) {
        Integer prevMoisture = state.getLastSoilMoisture();
        Integer min = getEffectiveSoilMoistureMin(plant);
        Integer max = getEffectiveSoilMoistureMax(plant);

        double evaporationRate = 1.5;
        int newMoisture = prevMoisture != null
                ? (int)(prevMoisture * evaporationRate)
                : (min + max) / 2;

        if (state.wateringActive) {
            newMoisture = Math.min(newMoisture + 20, max);
            state.decWateringTimer();
        }

        newMoisture += random.nextInt(20) - 10;
        newMoisture = Math.max(min, Math.min(max, newMoisture));

        state.setLastSoilMoisture(newMoisture);
        return newMoisture;
    }

    private Integer generateLight(PlantInstance plant, DeviceState state) {

//        if (hour < 7 || hour > 20) {
//            return 50;
//        }

        int maxLight = 1000;
        int avgLight = 180;

        int light = (int)(avgLight + random.nextDouble() * 0.4);
//        if (state.lightActive()) {
//            light += 800;
//            state.decLightTimer();
//        }

        return Math.min(maxLight, Math.max(50, light));
    }

    //for effective thresholds
    private BigDecimal getEffectiveTempMin(PlantInstance plant) {
        return plant.getTempMin() != null
                ? plant.getTempMin()
                : plant.getSpecies().getTempMin();
    }

    private BigDecimal getEffectiveTempMax(PlantInstance plant) {
        return plant.getSpecies().getTempMax();
    }

    private int getEffectiveSoilMoistureMin(PlantInstance plant) {
        return plant.getSoilMoistureMin() != null
                ? plant.getSoilMoistureMin()
                : plant.getSpecies().getSoilMoistureMin();
    }

    public void startWatering(Long plantId) {
        deviceStates.computeIfAbsent(plantId, k -> new DeviceState()).startWatering(20);
    }

    public void startHeating(Long plantId) {
        deviceStates.computeIfAbsent(plantId, k -> new DeviceState()).startHeating(20);
    }

//    public void startLight(Long plantId) {
//        deviceStates.computeIfAbsent(plantId, k -> new DeviceState()).startLight(10);
//    }

    public void startHumidifying(Long plantId) {
        deviceStates.computeIfAbsent(plantId, k -> new DeviceState()).startHum(20);
    }
    private int getEffectiveSoilMoistureMax(PlantInstance plant) {
        return plant.getSpecies().getSoilMoistureMax();
    }

    private static class DeviceState {
        private BigDecimal lastTemperature;
        private Integer lastSoilMoisture;
        private Integer lastHumidityAir;
        private boolean wateringActive;
        private int wateringTimer;
        private boolean heatingActive;
        private int heatingTimer;
        private boolean humActive;
        private int humTimer;

        public BigDecimal getLastTemperature() { return lastTemperature; }
        public void setLastTemperature(BigDecimal lastTemperature) { this.lastTemperature = lastTemperature; }
        public Integer getLastSoilMoisture() { return lastSoilMoisture; }
        public void setLastSoilMoisture(Integer lastSoilMoisture) { this.lastSoilMoisture = lastSoilMoisture; }
        public Integer getLastHumidityAir() { return lastHumidityAir; }
        public void setLastHumidityAir(Integer lastHumidityAir) { this.lastHumidityAir = lastHumidityAir; }


        public boolean wateringActive() { return wateringActive; }
        public void setWateringActive(boolean wateringActive) { this.wateringActive = wateringActive; }
        public void decWateringTimer() {
            if (wateringTimer > 0) wateringTimer--;
            if (wateringTimer == 0) wateringActive = false;
        }
        public void startWatering(int duration) {
            wateringActive = true;
            wateringTimer = duration;
        }

        public boolean heatingActive() { return heatingActive; }
        public void setHeatingActive(boolean heatingActive) { this.heatingActive = heatingActive; }
        public void decHeatingTimer() {
            if (heatingTimer > 0) heatingTimer--;
            if (heatingTimer == 0) heatingActive = false;
        }
        public void startHeating(int duration) {
            heatingActive = true;
            heatingTimer = duration;
        }

        public boolean humActive() { return humActive; }
        public void setHumActive(boolean humActive) { this.humActive = humActive; }
        public void decHumTimer() {
            if (humTimer > 0) humTimer--;
            if (humTimer == 0) humActive = false;
        }
        public void startHum(int duration) {
            humActive = true;
            humTimer = duration;
        }


    }

}