CREATE DATABASE IF NOT EXISTS id7373992_etutor;
DROP TABLE IF EXISTS id7373992_etutor.users;
DROP TABLE IF EXISTS id7373992_etutor.chapters;
DROP TABLE IF EXISTS id7373992_etutor.videos;
DROP TABLE IF EXISTS id7373992_etutor.unlocks;
CREATE TABLE id7373992_etutor.users(
	name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL);

CREATE TABLE id7373992_etutor.chapters(
	name VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	price DECIMAL(9, 2) NOT NULL,
	subject VARCHAR(255) NOT NULL);
INSERT INTO id7373992_etutor.chapters VALUES ('Whole Numbers', 'Whole numbers are positive numbers, including zero, without any decimal or fractional parts. They are numbers that represent whole things without pieces. The set of whole numbers is represented mathematically by the set: {0, 1, 2, 3, 4, 5...}.', '4.00', 'Mathematics');
INSERT INTO id7373992_etutor.chapters VALUES ('Number Patterns and Sequences', 'A Sequence is a set of things (usually numbers) that are in order. Each number in the sequence is called a term (or sometimes "element" or "member"), read Sequences and Series for a more in-depth discussion.', '4.50', 'Mathematics');
INSERT INTO id7373992_etutor.chapters VALUES ('Fractions', 'When a whole is divided into equal pieces, if fewer equal pieces are needed to make up the whole, then each piece must be larger. When two positive fractions have the same numerator, they represent the same number of parts, but in the fraction with the smaller denominator, the parts are larger.', '5.00', 'Mathematics');
INSERT INTO id7373992_etutor.chapters VALUES ('Decimals', 'The numbers we use in everyday life are decimal numbers, because they are based on 10 digits (0,1,2,3,4,5,6,7,8 and 9). "Decimal number" is often used to mean a number that uses a decimal point followed by digits that show a value smaller than one.', '5.00', 'Mathematics');
INSERT INTO id7373992_etutor.chapters VALUES ('Percentages', 'A percentage is a number or ratio expressed as a fraction of 100. It is often denoted using the percent sign, "%", or the abbreviations "pct.", "pct"; sometimes the abbreviation "pc" is also used. A percentage is a dimensionless number (pure number).', '5.00', 'Mathematics');
INSERT INTO id7373992_etutor.chapters VALUES ('Physical World', 'In a broader sense Physics is the study of the basic laws of nature and their manifestation. Physics is all about explaining diverse physical phenomena with the help of few concepts and laws. In Physics, there are two domains of interest macroscopic and microscopic.', '6.50', 'Physics');
INSERT INTO id7373992_etutor.chapters VALUES ('Units and Measurement', 'In physics and metrology, units are standards for measurement of physical quantities that need clear definitions to be useful. For example, a length is a physical quantity. The metre is a unit of length that represents a definite predetermined length.', '7.00', 'Physics');
INSERT INTO id7373992_etutor.chapters VALUES ('Motion in a Straight Line', 'If an object changes its position with respect to its surroundings with time, then it is called in motion. If an object does not change its position with respect to its surroundings with time, then it is called at rest.', '7.00', 'Physics');
INSERT INTO id7373992_etutor.chapters VALUES ('Motion in a Plane', 'Motion in a plane includes linear motion, rotational motion, and projectile motion. This lesson will focus on two-dimensional, linear motion of a single object, and linear motion of two objects moving relative to each other.', '7.50', 'Physics');
INSERT INTO id7373992_etutor.chapters VALUES ('Law of Motion', 'Newton\'s three laws of motion may be stated as follows: Every object in a state of uniform motion will remain in that state of motion unless an external force acts on it. Force equals mass times acceleration. For every action there is an equal and opposite reaction.', '8.00', 'Physics');
INSERT INTO id7373992_etutor.chapters VALUES ('The Solid State', 'Solid-state chemistry, also sometimes referred as materials chemistry, is the study of the synthesis, structure, and properties of solid phase materials, particularly, but not necessarily exclusively of, non-molecular solids.', '6.50', 'Chemistry');
INSERT INTO id7373992_etutor.chapters VALUES ('Solutions', 'In chemistry, a solution is a special type of homogeneous mixture composed of two or more substances. In such a mixture, a solute is a substance dissolved in another substance, known as a solvent.', '7.00', 'Chemistry');
INSERT INTO id7373992_etutor.chapters VALUES ('ElectroChemistry', 'ElectroChemistry is a sub-discipline of physical chemistry. It is concerned with chemical reactions involving charged particles and electron transfers. The relationship between electrical and chemical energy and the conversion of one to the other.', '8.00', 'Chemistry');
INSERT INTO id7373992_etutor.chapters VALUES ('Chemical Kinetics', 'Chemical kinetics is the study of chemical processes and rates of reactions. The rate of a chemical reaction usually has units of sec-1. Chemical kinetics may also be called reaction kinetics or simply "kinetics".', '8.50', 'Chemistry');
INSERT INTO id7373992_etutor.chapters VALUES ('Surface Chemistry', 'Surface chemistry deals with the study of phenomena that occur at the surfaces or interfaces of substances, like adsorption, heterogeneous catalysis, formation of colloids, corrosion, crystallization, dissolution, electrode processes and chromatography.', '9.00', 'Chemistry');

CREATE TABLE id7373992_etutor.videos(
	name VARCHAR(255) NOT NULL,
	author VARCHAR(255) NOT NULL,
	publisheddate VARCHAR(255) NOT NULL,
	url TEXT NOT NULL,
	chapter VARCHAR(255) NOT NULL);
INSERT INTO id7373992_etutor.videos VALUES ('Math - What is a Whole Number - English', 'Bodhaguru', 'Oct 25, 2013', 'X5ZaGh-Z3fQ', 'Whole Numbers');
INSERT INTO id7373992_etutor.videos VALUES ('Introduction to Whole Numbers', 'Justin Maths', 'Dec 12, 2015', 'BXAo_U4hqqo', 'Whole Numbers');

CREATE TABLE id7373992_etutor.unlocks(
	unlock_id INT(10) NOT NULL AUTO_INCREMENT,
	chapter VARCHAR(255) NOT NULL,
	userid VARCHAR(255) NOT NULL,
	PRIMARY KEY (unlock_id));