kill_data <- read.csv(file=file.choose(),fileEncoding="UTF-8-BOM",header=T)
head(kill_data)

####### Libraries
poop <- data.frame(kill_data)
colnames(poop) <- c("Baseline","LVA")



### Build data frames
kills <- data.frame(kill_data)

### Mean and SD
mean(kill_data$baseline.5)
mean(kill_data$mTac.5)

### Differences test
shapiro.test(tactic_one)
shapiro.test(tactic_two)

### Wilcox Tests
wilcox.test(kill_data$baseline.5,kill_data$mTac.5, alternative="greater")
wilcox.test(kill_data$baseline.6,kill_data$mTac.6, alternative="greater")
wilcox.test(kill_data$baseline.7,kill_data$mTac.7, alternative="greater")
wilcox.test(kill_data$baseline.8,kill_data$mTac.8, alternative="greater")


### Probabilistic Density Distribution Graph Function
densityFunction <- function(utility.df){
  colors = c("red","black")
  plot(range(0,max(utility.df)),range(0,0.155), type = "n", 
       xlab = "Critical Failures", ylab = "Density", 
       cex.lab=1.5, cex.axis=1.2)
  col_selection = 0
  for(x in names(utility.df)){
    col_selection = col_selection + 1
    dens <- density(utility.df[[x]])
    lines(dens, col=colors[col_selection], lwd=3)
  }
  legend("topright",legend=names(utility.df),
         col=colors, lty=1, cex=1.2)
}

densityFunction(poop)
