package test.hunt_test.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import test.hunt_test.model.ApplicationResource;

import java.util.List;

@Component
@Getter
@Setter
@Scope("singleton")
public class DistributionSync {
    private volatile static DistributionSync instance;
    private volatile boolean isDistributing = false;
    private List<ApplicationResource> applicationResources;

    private DistributionSync() {
    }

    public static DistributionSync getInstance() {
        if (instance == null) {
            synchronized (DistributionSync.class) {
                if (instance == null) {
                    instance = new DistributionSync();
                }
            }
        }
        return instance;
    }

    public synchronized void startDistributing() {
        isDistributing = true;
    }


    public synchronized void stopDistributing() {
        isDistributing = false;
        applicationResources.clear();

    }


    public synchronized boolean isDistributing() {
        return isDistributing;
    }

    public synchronized void setApplicationResources(List<ApplicationResource> applicationResources) {
        this.applicationResources = applicationResources;
    }

    public synchronized List<ApplicationResource> getApplicationResources() {
        return applicationResources;
    }

    public synchronized void addApplicationResource(ApplicationResource applicationResource) {
        applicationResources.add(applicationResource);
    }

    public synchronized ApplicationResource getOneApplicationResource() {
        return applicationResources.remove(0);
    }


}
