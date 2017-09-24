k = read.csv2(file = "C:\\Users\\david\\Documents\\MEGA\\VSB-TUO\\Ing\\2_semestr\\MADII\\data.txt",header = FALSE)
k$V8 <- NULL

hc <- hclust(dist(k), "ave")
plot(hc)

rect.hclust(hc, k=2, border="red") 

y<-dist(k)
clust<-hclust(y, "ave")
groups<-cutree(clust, k=2)
k<-cbind(k,groups)

x1<- subset(k, groups==1)
x2<- subset(k, groups==2)


k = read.csv2(file = "C:\\Users\\david\\Documents\\MEGA\\VSB-TUO\\Ing\\2_semestr\\MADII\\annulus.csv",header = FALSE)
hc <- hclust(dist(k), "ave")
plot(hc)
y<-dist(k)
clust<-hclust(y, "ave")
groups<-cutree(clust, k=5)
k<-cbind(k,groups)
