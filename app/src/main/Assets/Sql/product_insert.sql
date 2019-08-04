
/**

-- Final Project - CSIS4175 - Insert Products into Product Table

CREATE TABLE Product ( 
    ProductID   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    Name        VARCHAR(40) NOT NULL,
    Description VARCHAR(200) NOT NULL,
    Category    VARCHAR(50) NOT NULL,
    PictureURL  VARCHAR(200) NOT NULL,
    Price       FLOAT(6,2) NOT NULL,
    Highlight   BOOLEAN NOT NULL,
    CONSTRAINT  Product_PK PRIMARY KEY(ProductID)
);

*/

INSERT INTO Product (Name,Description,Category,PictureURL,Price,Highlight) VALUES
  ('Bud Light', 'Bud Light Description', 'Beer','BudLight.png',2.99,TRUE),
  ('Coors Light', 'Coors Light Description', 'Beer','CoorsLight.png',2.99,FALSE),
  ('Corona Extra', 'Corona Extra Description', 'Beer','CoronaExtra.png',2.99,FALSE),
  ('Miller Lite', 'Miller Lite Description', 'Beer','MillerLite.png',2.99,FALSE),
  ('Stella Artois', 'Stella Artois Description', 'Beer','StellaArtois.png',2.99,FALSE),
  ('Michelob Ultra', 'Michelob Ultra Description', 'Beer','MichelobUltra.png',2.99,FALSE),
  ('Corona Light', 'Corona Light Description', 'Beer','CoronaLight.png',2.99,FALSE),
  ('Budweiser', 'Budweiser Description', 'Beer','Budweiser.png',2.99,FALSE),
  ('Modelo Especial', 'Modelo Especial Description', 'Beer','ModeloEspecial.png',2.99,FALSE),
  ('Heineken Lager', 'Heineken Lager Description', 'Beer','HeinekenLager.png',2.99,FALSE),
  ('Pabst Blue Ribbon', 'Pabst Blue Ribbon Description', 'Beer','PabstBlueRibbon.png',2.99,FALSE),
  ('Dos Equis Lager', 'Dos Equis Lager Description', 'Beer','DosEquisLager.png',2.99,FALSE),
  ('Lagunitas IPA', 'Lagunitas IPA Description', 'Beer','LagunitasIPA.png',3.99,TRUE),
  ('Lagunitas DayTime IPA', 'Lagunitas DayTime IPA Description', 'Beer','LagunitasDayTimeIPA.png',3.99,FALSE),
  ('Founders All Day IPA', 'Founders All Day IPA Description', 'Beer','FoundersAllDayIPA.png',3.99,FALSE),
  ('Goose Island IPA', 'Goose Island IPA Description', 'Beer','GooseIslandIPA.png',3.99,FALSE),
  ('Lord Hobo Boom Sauce IPA', 'Lord Hobo Boom Sauce IPA Description', 'Beer','LordHoboBoomSauceIPA.png',3.99,FALSE),
  ('Ballast Point Sculpin IPA', 'Ballast Point Sculpin IPA Description', 'Beer','BallastPointSculpinIPA.png',3.99,FALSE),
  ('Oyster Bay Sauvignon Blanc', 'Oyster Bay Sauvignon Blanc Description', 'Wine','OysterBaySauvignonBlanc.png',28.99,TRUE),
  ('Barefoot Pinot Grigio', 'Barefoot Pinot Grigio Description', 'Wine','BarefootPinotGrigio.png',12.99,FALSE),
  ('Starborough Sauvignon Blanc', 'Starborough Sauvignon Blanc Description', 'Wine','StarboroughSauvignonBlanc.png',23.99,FALSE),
  ('Whitehaven Sauvignon Blanc', 'Whitehaven Sauvignon Blanc Description', 'Wine','WhitehavenSauvignonBlanc.png',28.99,FALSE),
  ('Cavit Pinot Grigio', 'Cavit Pinot Grigio Description', 'Wine','CavitPinotGrigio.png',19.99,FALSE),
  ('YellowTailShiraz', 'YellowTailShiraz Description', 'Wine','YellowTailShiraz.png',39.99,TRUE),
  ('Jam Jar Sweet Shiraz', 'Jam Jar Sweet Shiraz Description', 'Wine','JamJarSweetShiraz.png',28.99,FALSE),
  ('Apothic Red', 'Apothic Red Description', 'Wine','ApothicRed.png',19.99,FALSE),
  ('Veuve Clicquot Brut Yellow Label', 'Veuve Clicquot Brut Yellow Label Description', 'Wine','VeuveClicquotBrutYellowLabel.png',99.99,FALSE),
  ('Moet & Chandon Ice Imperial', 'Moet & Chandon Ice Imperial Description', 'Wine','MoeteChandonIceImperial.png',95.99,TRUE),
  ('Laurent-Perrier Brut Champagne', 'Laurent-Perrier Brut Champagne Description', 'Wine','LaurentPerrierBrutChampagne.png',99.99,FALSE),
  ('Dark Horse Rose', 'Dark Horse Rose Description', 'Wine','DarkHorseRose.png',15.99,TRUE),
  ('Barefoot Rose', 'Barefoot Rose Description', 'Wine','BarefootRose.png',12.99,FALSE),
  ('Miraval Provence Rose', 'Miraval Provence Rose Description', 'Wine','MiravalProvenceRose.png',38.99,FALSE),
  ('Smirnoff No21 Vodka', 'Smirnoff No21 Vodka Description', 'Distilled','SmirnoffNo21Vodka.png',99.91,FALSE),
  ('Absolut Vodka', 'Absolut Vodka Description', 'Distilled','AbsolutVodka.png',47.99,TRUE),
  ('SKYYVodka', 'SKYYVodka Description', 'Distilled','SKYYVodka.png',38.99,FALSE),
  ('Johnnie Walker Red Label', 'Johnnie Walker Red Label Description', 'Distilled','JohnnieWalkerRedLabel.png',31.99,TRUE),
  ('Chivas Regal 12Year', 'Chivas Regal 12Year Description', 'Distilled','ChivasRegal12Year.png',41.99,FALSE),
  ('J&BRareBlendedScotch', 'J&BRareBlendedScotch Description', 'Distilled','JeBRareBlendedScotch.png',25.99,FALSE),
  ('Red Bull', 'Red Bull Description', 'Extras','RedBull.png',3.99,TRUE),
  ('Monster Energy Drink', 'Monster Energy Drink Description', 'Extras','MonsterEnergyDrink.png',3.99,FALSE),
  ('Minute Maid Lemonade', 'Minute Maid Lemonade Description', 'Extras','MinuteMaidLemonade.png',3.99,TRUE),
  ('TropicanaOrangeJuice', 'TropicanaOrangeJuice Description', 'Extras','TropicanaOrangeJuice.png',5.99,FALSE);


