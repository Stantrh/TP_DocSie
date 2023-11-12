-- Création de la table Article
CREATE TABLE Article (
    TITRE VARCHAR2(500) NOT NULL,
    RESUME VARCHAR2(500),
    TYPEARTICLE VARCHAR2(500),
    PRIMARY KEY (TITRE)
);

-- Création de la table Chercheur
CREATE TABLE Chercheur (
    EMAIL VARCHAR2(500) NOT NULL,
    NOMCHERCHEUR VARCHAR2(500),
    PRENOMCHERCHEUR VARCHAR2(500),
    URLCHERCHEUR VARCHAR2(500),
    PRIMARY KEY (EMAIL)
);

-- Création de la table Laboratoire
CREATE TABLE Laboratoire (
    NOMLABO VARCHAR2(500) NOT NULL,
    SIGLELABO VARCHAR2(500),
    ADRESSELABO VARCHAR2(200),
    URLLABO VARCHAR2(500),
    PRIMARY KEY (NOMLABO)
);

-- Création de la table Support
CREATE TABLE Support (
    NOMSUPPORT VARCHAR2(500) NOT NULL,
    TYPESUPPORT VARCHAR2(500),
    PRIMARY KEY (NOMSUPPORT)
);

-- Création de la table Annotation
CREATE TABLE Annotation (
    LIBELLE VARCHAR2(500) NOT NULL,
    PRIMARY KEY (LIBELLE)
);

-- Création de la table Ecrire
CREATE TABLE Ecrire (
    TITRE VARCHAR2(500) NOT NULL,
    EMAIL VARCHAR2(500) NOT NULL,
    PRIMARY KEY (TITRE, EMAIL),
    FOREIGN KEY (TITRE) REFERENCES Article(TITRE),
    FOREIGN KEY (EMAIL) REFERENCES Chercheur(EMAIL)
);

-- Création de la table Publier
CREATE TABLE Publier (
    TITRE VARCHAR2(500) NOT NULL,
    NOMSUPPORT VARCHAR2(500) NOT NULL,
    ANNEE_PUBLICATION NUMBER(38),
    PRIMARY KEY (TITRE, NOMSUPPORT),
    FOREIGN KEY (TITRE) REFERENCES Article(TITRE),
    FOREIGN KEY (NOMSUPPORT) REFERENCES Support(NOMSUPPORT)
);

-- Création de la table Travailler
CREATE TABLE Travailler (
    EMAIL VARCHAR2(500) NOT NULL,
    NOMLABO VARCHAR2(500) NOT NULL,
    PRIMARY KEY (EMAIL, NOMLABO),
    FOREIGN KEY (EMAIL) REFERENCES Chercheur(EMAIL),
    FOREIGN KEY (NOMLABO) REFERENCES Laboratoire(NOMLABO)
);

-- Création de la table Annoter
CREATE TABLE Annoter (
    EMAIL VARCHAR2(500) NOT NULL,
    TITRE VARCHAR2(500) NOT NULL,
    LIBELLE VARCHAR2(500) NOT NULL,
    PRIMARY KEY (EMAIL, TITRE, LIBELLE),
    FOREIGN KEY (EMAIL) REFERENCES Chercheur(EMAIL),
    FOREIGN KEY (TITRE) REFERENCES Article(TITRE),
    FOREIGN KEY (LIBELLE) REFERENCES Annotation(LIBELLE)
);

-- Création de la table Noter
CREATE TABLE Noter (
    EMAIL VARCHAR2(500) NOT NULL,
    TITRE VARCHAR2(500) NOT NULL,
    NOTE NUMBER(38),
    PRIMARY KEY (EMAIL, TITRE),
    FOREIGN KEY (EMAIL) REFERENCES Chercheur(EMAIL),
    FOREIGN KEY (TITRE) REFERENCES Article(TITRE)
);

/*
##### INSERTIONS ##### PROBLEME LIGNE 366
*/

-- Insertion des données dans la table "Article"
INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Adding Structure to Unstructured Data', 'We develop a new schema for unstructured data. Traditional schemas resemble the type systems of programming languages.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('A User-centric Framework for Accessing Biological Sources and Tools', 'We study the representation and querying of XML with incomplete information. We consider a simple model for XML data and their DTDs, a very simple query language, and a representation system for incomplete information in the spirit of the representations systems developed by Imielinski and Lipski for relational databases.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('PDiffView: Viewing the Difference in Provenance of Workflow Results', 'Provenance Difference Viewer (PDiffView) is a prototype based on these algorithms for differencing runs of SPFL specifications.', 'Court');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Automata and Logics for Words and Trees over an Infinite Alphabet', 'In a data word or a data tree each position carries a label from a finite alphabet and a data value from some infinite domain. These models have been considered in the realm of semistructured data, timed automata and extended temporal logics. This paper survey several know results on automata and logics manipulating data words and data trees, the focus being on their relative expressive power and decidability.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Representing and Querying XML with Incomplete Information', 'We study the representation and querying of XML with incomplete information. We consider a simple model for XML data and their DTDs, a very simple query language, and a representation system for incomplete information in the spirit of the representations systems developed by Imielinski and Lipski for relational databases. In the scenario we consider, the incomplete information about an XML document is continuously enriched by successive queries to the document.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('The TLA+ Proof System: Building a Heterogeneous Verification Platform', 'Model checking has proved to be an efficient technique for finding subtle bugs in concurrent and distributed algorithms and systems. However, it is usually limited to the analysis of small instances of such systems, due to the problem of state space explosion. When model checking finds no more errors, one can attempt to verify the correctness of a model using theorem proving, which also requires efficient tool support.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Partial reversal acyclicity', 'Partial Reversal (PR) is a link reversal algorithm which ensures that an initially directed acyclic graph (DAG) is eventually a destination-oriented DAG. While proofs exist to establish the acyclicity property of PR, they rely on assigning labels to either the nodes or the edges in the graph. In this work we show that such labeling is not necessary and outline a simpler direct proof of the acyclicity property.', 'Court');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Reliably Detecting Connectivity Using Local Graph Traits', 'This paper studies local graph traits and their relationship with global graph properties. Specifically, we focus on graph k-connectivity. First we prove a negative result that shows there does not exist a local graph trait which perfectly captures graph k-connectivity. We then present three different local graph traits which can be used to reliably predict the k-connectivity of a graph with varying degrees of accuracy.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Generalized Universality', 'This paper presents, two decades after k-set consensus was introduced, the generalization with k > 1 of state machine replication. We show that with k-set consensus, any number of processes can emulate k state machines of which at least one remains highly available. While doing so, we also generalize the very notion of consensus universality.', 'Long');

INSERT INTO Article (TITRE, RESUME, TYPEARTICLE) VALUES
('Transactional Memory: Glimmer of a Theory', 'Transactional memory (TM) is a promising paradigm for concurrent programming. This paper is an overview of our recent theoretical work on defining a theory of TM. We first recall some TM correctness properties and then overview results on the inherent power and limitations of TMs.', 'Tutoriel');


-- Insertion des données dans la table "Chercheur"
INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('peter@cis.upenn.edu', 'Buneman', 'Peter', 'http://homepages.inf.ed.ac.uk/opb/');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('cohen@lri.fr', 'Cohen-Boulakia', 'Sarah', 'http://www.lri.fr/~cohen');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('chris@lri.fr', 'Froidevaux', 'Christine', 'http://www.lri.fr/~chris/');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('susan@cis.upenn.edu', 'Davidson', 'Susan', 'http://www.cis.upenn.edu/~susan/');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('luc.segoufin@inria.fr', 'Segoufin', 'Luc', 'http://www-rocq.inria.fr/~segoufin/');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('lamport@microsoft.com', 'Lamport', 'Leslie', 'http://www.lamport.org/');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('lynch@theory.csail.mit.edu', 'Lynch', 'Nancy', 'http://people.csail.mit.edu/lynch/');

INSERT INTO Chercheur (EMAIL, NOMCHERCHEUR, PRENOMCHERCHEUR, URLCHERCHEUR) VALUES
('Rachid.Guerraoui@epfl.ch', 'Guerraoui', 'Rachid', 'http://lpdwww.epfl.ch/rachid/');



-- Insertion des données dans la table "Laboratoire"
INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Laboratory for Foundations of Computer Science', 'LFCS', 'LFCS, School of Informatics Crichton Stree Edinburgh EH8 9LE', NULL);

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Department of Computer and Information Science University of Pennsylvania', 'CIS', '305 Levine/572 Levine North Department of Computer and Information Science  University of Pennsylvania  Levine Hall  3330 Walnut Street  Philadelphia, PA 19104-6389', NULL);

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Laboratoire de Recherche en Informatique', 'LRI', 'Bât 490 Université Paris-Sud 11 91405 Orsay Cedex France', NULL);

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Laboratoire Spécification et Vérification', 'LSV', 'ENS de Cachan, 61 avenue du Président Wilson, 94235 CACHAN Cedex, FRANCE', NULL);

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Distributed Programming Laboratory', 'LPD', 'Bat INR 326 Station 14 1015 Lausanne Switzerland', 'http://lpd.epfl.ch/site/');

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Theory of Distributed Systems', 'TDS', '32 Vassar Street (32-G672A) Cambridge, MA 02139, USA', 'http://groups.csail.mit.edu/tds/');

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('Microsoft Corporation', 'Microsoft', '1065 La Avenida Mountain View, CA 94043USA', 'http://research.microsoft.com');

INSERT INTO Laboratoire (NOMLABO, SIGLELABO, ADRESSELABO, URLLABO) VALUES
('INRIA Saclay - Ile-de-France', 'INRIA Saclay', 'Domaine de Voluceau Rocquencourt - BP 105 78153 Le Chesnay Cedex, France', 'http://www.inria.fr/centre/saclay');


-- Insertion des données dans la table "Support"
INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('ICDT', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('DILS', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('TODS', 'Journal');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('VLDB', 'Journal');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('CLS', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('CAV', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('CONCUR', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('OPODIS', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('PODC', 'Conference');

INSERT INTO Support (NOMSUPPORT, TYPESUPPORT) VALUES
('ICTAC', 'Conference');


-- Insertion des données dans la table "Annotation"
INSERT INTO Annotation (LIBELLE) VALUES
('data');

INSERT INTO Annotation (LIBELLE) VALUES
('bio');

INSERT INTO Annotation (LIBELLE) VALUES
('workflow');

INSERT INTO Annotation (LIBELLE) VALUES
('theory');

INSERT INTO Annotation (LIBELLE) VALUES
('XML');

INSERT INTO Annotation (LIBELLE) VALUES
('Concurrency');

INSERT INTO Annotation (LIBELLE) VALUES
('TLA');

INSERT INTO Annotation (LIBELLE) VALUES
('Consencus');

INSERT INTO Annotation (LIBELLE) VALUES
('Graph');

INSERT INTO Annotation (LIBELLE) VALUES
('Reliability');

-- Insertion des données dans la table "Ecrire"
INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('peter@cis.upenn.edu', 'Adding Structure to Unstructured Data');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('susan@cis.upenn.edu', 'Adding Structure to Unstructured Data');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('susan@cis.upenn.edu', 'A User-centric Framework for Accessing Biological Sources and Tools');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('cohen@lri.fr', 'A User-centric Framework for Accessing Biological Sources and Tools');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('chris@lri.fr', 'A User-centric Framework for Accessing Biological Sources and Tools');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('luc.segoufin@inria.fr', 'Automata and Logics for Words and Trees over an Infinite Alphabet');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('luc.segoufin@inria.fr', 'Representing and Querying XML with Incomplete Information');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('Rachid.Guerraoui@epfl.ch', 'Generalized Universality');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('Rachid.Guerraoui@epfl.ch', 'Transactional Memory: Glimmer of a Theory');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('lynch@theory.csail.mit.edu', 'Reliably Detecting Connectivity Using Local Graph Traits');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('lynch@theory.csail.mit.edu', 'Partial reversal acyclicity');

INSERT INTO Ecrire (EMAIL, TITRE) VALUES
('lamport@microsoft.com', 'The TLA+ Proof System: Building a Heterogeneous Verification Platform');


-- Insertion des données dans la table "Publier"
INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Adding Structure to Unstructured Data', 'ICDT', 1997);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('A User-centric Framework for Accessing Biological Sources and Tools', 'DILS', 2005);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Representing and Querying XML with Incomplete Information', 'TODS', 2006);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('PDiffView: Viewing the Difference in Provenance of Workflow Results', 'VLDB', 2009);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Automata and Logics for Words and Trees over an Infinite Alphabet', 'CLS', 2006);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('The TLA+ Proof System: Building a Heterogeneous Verification Platform', 'ICTAC', 2009);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Partial reversal acyclicity', 'PODC', 2011);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Reliably Detecting Connectivity Using Local Graph Traits', 'OPODIS', 2010);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Generalized Universality', 'CONCUR', 2011);

INSERT INTO Publier (TITRE, NOMSUPPORT, ANNEE_PUBLICATION) VALUES
('Transactional Memory: Glimmer of a Theory', 'CAV', 2010);


-- Insertion des données dans la table "Travailler"
INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('peter@cis.upenn.edu', 'Laboratory for Foundations of Computer Science');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('susan@cis.upenn.edu', 'Department of Computer and Information Science University of Pennsylvania');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('peter@cis.upenn.edu', 'Department of Computer and Information Science University of Pennsylvania');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('cohen@lri.fr', 'Laboratoire de Recherche en Informatique');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('chris@lri.fr', 'Laboratoire de Recherche en Informatique');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('luc.segoufin@inria.fr', 'Laboratoire Spécification et Vérification');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('luc.segoufin@inria.fr', 'INRIA Saclay - Ile-de-France');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('lamport@microsoft.com', 'Microsoft Corporation');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('lynch@theory.csail.mit.edu', 'Theory of Distributed Systems');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('Rachid.Guerraoui@epfl.ch', 'Distributed Programming Laboratory');

INSERT INTO Travailler (EMAIL, NOMLABO) VALUES
('Rachid.Guerraoui@epfl.ch', 'INRIA Saclay - Ile-de-France');



-- Insertion des données dans la table "Annoter"
INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('luc.segoufin@inria.fr', 'Adding Structure to Unstructured Data', 'data');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('peter@cis.upenn.edu', 'A User-centric Framework for Accessing Biological Sources and Tools', 'bio');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('peter@cis.upenn.edu', 'Adding Structure to Unstructured Data', 'XML');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('peter@cis.upenn.edu', 'PDiffView: Viewing the Difference in Provenance of Workflow Results', 'workflow');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('cohen@lri.fr', 'Automata and Logics for Words and Trees over an Infinite Alphabet', 'theory');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('lamport@microsoft.com', 'The TLA+ Proof System: Building a Heterogeneous Verification Platform', 'TLA');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('lynch@theory.csail.mit.edu', 'Generalized Universality', 'Consencus');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('lynch@theory.csail.mit.edu', 'Transactional Memory: Glimmer of a Theory', 'Concurrency');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('Rachid.Guerraoui@epfl.ch', 'Partial reversal acyclicity', 'Graph');

INSERT INTO Annoter (EMAIL, TITRE, LIBELLE) VALUES
('Rachid.Guerraoui@epfl.ch', 'Reliably Detecting Connectivity Using Local Graph Traits', 'Reliability');



--Insertion des données dans la table "Noter"
INSERT INTO NOTER (Email, Titre, Note)
VALUES ('luc.segoufin@inria.fr', 'Adding Structure to Unstructured Data', 4);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('luc.segoufin@inria.fr', 'Automata and Logics for Words and Trees over an Infinite Alphabet', 1);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('luc.segoufin@inria.fr', 'A User-centric Framework for Accessing Biological Sources and Tools', 4);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('luc.segoufin@inria.fr', 'PDiffView: Viewing the Difference in Provenance of Workflow Results', 5);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('luc.segoufin@inria.fr', 'Representing and Querying XML with Incomplete Information', 1);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('peter@cis.upenn.edu', 'A User-centric Framework for Accessing Biological Sources and Tools', 2);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('peter@cis.upenn.edu', 'Automata and Logics for Words and Trees over an Infinite Alphabet', 1);


INSERT INTO NOTER (Email, Titre, Note)
VALUES ('cohen@lri.fr', 'A User-centric Framework for Accessing Biological Sources and Tools', 2);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('cohen@lri.fr', 'PDiffView: Viewing the Difference in Provenance of Workflow Results', 1);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('Rachid.Guerraoui@epfl.ch', 'Adding Structure to Unstructured Data', 1);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('Rachid.Guerraoui@epfl.ch', 'Automata and Logics for Words and Trees over an Infinite Alphabet', 4);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('Rachid.Guerraoui@epfl.ch', 'A User-centric Framework for Accessing Biological Sources and Tools', 2);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('Rachid.Guerraoui@epfl.ch', 'PDiffView: Viewing the Difference in Provenance of Workflow Results', 1);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('Rachid.Guerraoui@epfl.ch', 'Representing and Querying XML with Incomplete Information', 5);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('lamport@microsoft.com', 'A User-centric Framework for Accessing Biological Sources and Tools', 3);

INSERT INTO NOTER (Email, Titre, Note)
VALUES ('lamport@microsoft.com', 'Automata and Logics for Words and Trees over an Infinite Alphabet', 4);



-- Triggers :
-- Trigger a)
CREATE OR REPLACE TRIGGER verifierNoteChercheur
BEFORE INSERT ON Noter
FOR EACH ROW
declare
    v_coAuteur NUMBER;
begin
    -- Vérifier le nombre de co-auteurs pour l'article
    select COUNT(email) INTO v_coAuteur
    from Ecrire
    where titre = :NEW.titre
      and email = :NEW.email;

    -- Si le count vaut + de 0, ça veut dire qu'il est co auteur
    if v_coAuteur > 0 then
        RAISE_APPLICATION_ERROR(-20023, 'Insertion imossible : Un chercheur auteur/co-auteur d''un article ne peut pas le noter.');
    end if;
END;


-- Trigger b)
CREATE TABLE log_chercheurs (
    utilisateur VARCHAR2(500) NOT NULL,
    date_action DATE NOT NULL,
    type_action VARCHAR2(500) NOT NULL
);
CREATE OR REPLACE TRIGGER loggerDAnnotation
AFTER INSERT OR UPDATE ON annoter
FOR EACH ROW
BEGIN
    -- Enregistrer l'action dans la table log_chercheurs
    IF INSERTING THEN
        INSERT INTO log_chercheurs (utilisateur, date_action, type_action)
        VALUES (USER, SYSDATE, 'Insertion dans la table annoter');
    ELSIF UPDATING THEN
        INSERT INTO log_chercheurs (utilisateur, date_action, type_action)
        VALUES (USER, SYSDATE, 'Mise à jour dans la table annoter');
    END IF;
END;


commit;