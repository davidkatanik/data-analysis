#Nacteni dat
data<-as.data.frame(read.csv("./../data.txt", header = F, sep = ","))
colnames(data) <- c("forkVA","forkkW","type","sector","serviceId")
data

data$serviceId=as.factor(data$serviceId)
data$type=as.factor(data$type)
data$type
data=na.omit(data)

#Kolacovy graf
slices <- c(10, 12, 4, 16, 8) 
lbls <- c("US", "UK", "Australia", "Germany", "France")
pct <- round(slices/sum(slices)*100)
lbls <- paste(lbls, pct) # add percents to labels 
lbls <- paste(lbls,"%",sep="") # ad % to labels 
pie(table(data$type), col=rainbow(length(lbls)),
    main="Rozložení jednotlivých subjektů")

library(ggplot2)
bp<- ggplot(table(data$type), aes(x="asasas", y=data$forkkW, fill=data$type))+
  geom_bar(width = 1, stat = "identity")
bp

pie <- bp + coord_polar("y", start=0)
pie + scale_fill_brewer(palette="Dark2")
pie


#Tabulka cetnosti pro typ
typeTable <- table(data$type)
pie(typeTable)
barplot(typeTable, las = 3)

par(mar=c(11,3,1,1)) 
ylim <- c(0, 1.2*max(typeTable))
## Plot, and store x-coordinates of bars in xx
xx <- barplot(typeTable, xaxt = 'n', xlab = '', width = 0.95, ylim = ylim, space = 1, col = "blue",
              main = "Pocet mereni pro jednotlive subjekty", 
              ylab = "Pocet")
## Add text at top of bars
text(x = xx, y = typeTable, label = typeTable, pos = 3, cex = 0.8, col = "red")
## Add x-axis labels 
axis(1, at=xx, labels=names(typeTable), las=2)


#Spotreba pro vyrobce v kW
spotrebaVyrobce = aggregate(data$forkkW, by=list(data$type), FUN=sum)
spotrebaVyrobce$Group.1=as.factor(spotrebaVyrobce$Group.1)
par(mar=c(11,3,1,1)) 
ylim <- c(0, 1.1*max(spotrebaVyrobce$x))
xx <- barplot(spotrebaVyrobce$x, xaxt = 'n', xlab = '', width = 0.95, ylim = ylim, space = 1, col = "blue",
              main = "Spotreba elektricke energie v kW", 
              ylab = "Spotreba v KW")
text(x = xx, y = spotrebaVyrobce$x, label = round(spotrebaVyrobce$x,0), pos = 3, cex = 0.8, col = "red")
axis(1, at=xx, labels=spotrebaVyrobce$Group.1, las=2)

#Pokusy s grafy
library(ggplot2)
library(plyr)
library(reshape2)

qplot(data$type, geom = "auto")
data$type


#Rozlozeni podle typy
d <- density(typeTable) # returns the density data 
plot(d) # plots the results

length(data$forkVA)
length(data$forkkW)

plot(data$forkVA,data$type, xlab = "fork VA", ylab = "fork kW", col=ifelse(data$type == "University", "red", "black"))

#Summary
summary(data, maxsum = 50)

mean(data$forkVA)
mean(data$forkkW)




# Filtrovani pouze univerzity
dataUniv= data[ data$type %in% c("University"),  ]
dataUniv

hist(table(dataUniv))

#KNN
train <- rbind(iris3[1:25,,1], iris3[1:25,,2], iris3[1:25,,3])
test <- rbind(iris3[26:50,,1], iris3[26:50,,2], iris3[26:50,,3])
cl <- factor(c(rep("s",25), rep("c",25), rep("v",25)))
knn(train, test, cl, k = 3, prob=TRUE)
attributes(.Last.value)


