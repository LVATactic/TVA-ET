latency_differences <- read.csv(file=file.choose(),fileEncoding="UTF-8-BOM",header=T)
head(latency_differences)

### Libraries


### Mean and SD
mean(latency_differences$Baseline)
sd(latency_differences$Baseline)
mean(latency_differences$TacSim)
sd(latency_differences$TacSim)

### Wilcox
wilcox.test(latency_differences$Baseline,latency_differences$TacSim)

### F-Test
var.test(latency_differences$Baseline,latency_differences$TacSim)

wilcox.test(kill_data$Baseline..Critical.Failures.,kill_data$TacSim..Critical.Failures.)
