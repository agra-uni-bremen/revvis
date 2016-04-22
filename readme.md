# RevVis #

*A Tool for the Visualization of Structures and Properties in Reversible Circuits*

---------------------------------------

Robert Wille, Jannis Stoppe, Eleonora Schönborn, Kamalika Datta, and Rolf Drechsler
University of Bremen, Germany
http://www.informatik.uni-bremen.de/agra/eng/revvis.php

This software is licensed under the GPLv3 license. See the LICENSE file for
details.

If you have any questions feel free to contact us using
revvis@informatik.uni-bremen.de

## Overview

The recent interest in reversible computation led to plenty
of (automatic) approaches for the design of the corresponding circuits.
While this automation is desired in order to provide a proper support
for the design of complex functionality, often a manual consideration and
human intuition enable improvements or provide new ideas for design so-
lutions. However, this manual interaction requires a good understanding
of the structure or the properties of a reversible cascade which, with in-
creasing circuit size, becomes harder to grasp. Visualization techniques
that abstract irrelevant details and focus on intuitively displaying im-
portant structures or properties provide a solution to this problem and
have already successfully been applied in other domains such as design
of conventional software, hardware debugging, or Boolean satisfiability.
In this work, we introduce RevVis, a graphical interface which visualizes
structures and properties of reversible circuits. RevVis collects relevant
data of a given reversible cascade and presents it in a simple but intuitive
fashion. By this, RevVis unveils information on characteristic structures
and properties of reversible circuits that could be utilized for further
optimization. A case study demonstrates this by considering circuits ob-
tained from several synthesis approaches.

## Usage

RevVis was written using java and the libgdx framework, i.e. you will need a
working Java Runtime Environment to run RevVis. You can build and run RevVis
using the gradle script via
	./gradlew desktop:run

You are prompted with a dialogue to open a circuit when starting the program.
If you cancel the selection of a file, an example circuit will be loaded
instead. Using the GUI, you may load your own circuit later on as well. All
loaded circuits must be provided in the RevLib-format (see www.revlib.org for
details).

Shift the viewport by dragging with the left mouse button, zoom in or out using
the mouse wheel. On the bottom of the screen, 10 buttons offer visualization
presets that show different aspects of the loaded circuit. These presets can
also be selected using the keys 0 - 9.

## Reference

If you use RevVis for your research, we would be thankful if you refer to the
tool by citing the following publication: 


	@inproceedings{revvis,
		author = {Robert Wille and Jannis Stoppe and Eleonora Sch{\"o}nborn and Kamalika Datta and Rolf Drechsler},
		title = {RevVis: Visualization of Structures and Properties in Reversible Circuits},
		booktitle = {Reversible Computation},
		publisher = {Springer},
		year = {2014},
		note = {{RevVis} is available at http://www.informatik.uni-bremen.de/agra/eng/revvis.php}
	}
