# EMS - Aplicație web pentru gestionarea pontajelor angajaților din cadrul unei firme

## Pașii pentru rularea proiectului de backend

### 1. Setup Java

- descărcarea **JDK 17** de pe site-ul
  oficial: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- setarea variabilei de mediu **JAVA_HOME** (Environment Variables ->
  User variables -> New) care să indice către folderul jdk-17 (folderul părinte pentru bin)
  și a variabilei de mediu din Path (Environment Variables ->
  System variables -> Path -> Edit -> New) care să indice către folderul bin (exemplu:
  C:\Program Files\Java\jdk-17\bin)

### 3. Setup MySQL

- instalarea serverului de MySQL de la: https://dev.mysql.com/downloads/mysql/
- instalarea MySQL Workbench
- crearea unei noi conexiuni cu serverul de MySQL în MySQL Workbench, pe portul 3306
- crearea bazei de date cu comanda:

```mysql
create database ems;
```

### 3. Clonarea proiectului

- crearea unui folder rădăcină (exemplu: ems-backend)
- rularea comenzii:

```bash
git clone ${repo_url}
```

### 4. Pornirea aplicației

- deschiderea unui terminal în folderul rădăcină (ems-backend)
- instalarea tuturor dependențelor prin comanda:

```bash
./gradlew build
```

- rularea aplicației prin comanda:

```bash
./gradlew bootRun
```

