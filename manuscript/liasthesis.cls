\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{LiasThesis}[2024/02/28 Lias Thesis class]

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% CLASSE ÉTENDUE AU FORMAT MANUSCRIPT DE THÈSE %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\LoadClass[a4paper,12pt,twoside,openright,final]{memoir}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% PACKAGES REQUIS POUR LIASTHESIS %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\RequirePackage[utf8]{inputenc} %% On veut tous les caractères et diactriques
\RequirePackage[T1]{fontenc} %% T1 est suffisament complet pour avoir textbf + textsc
\RequirePackage{tabto} %% Pour aligner arbitrairement du text
\RequirePackage{graphicx} %% À titre personnel
\RequirePackage{xcolor}

% \ifpdftex
%   % Maybe issue pdftex logs
% \fi
% \ifluatex
%   % Maybe issue luatex logs
% \fi
% \ifxetex
%   % Maybe issue xetex logs
% \fi

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% CUSTOM COLORS %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\definecolor{nord0}{HTML}{2e3440}
\definecolor{nord1}{HTML}{3b4252}
\definecolor{nord2}{HTML}{434c5e}
\definecolor{nord3}{HTML}{4c566a}
\definecolor{nord4}{HTML}{d8dee9}
\definecolor{nord5}{HTML}{e5e9f0}
\definecolor{nord6}{HTML}{eceff4}
\definecolor{nord7}{HTML}{8fbcbb}
\definecolor{nord8}{HTML}{88c0d0}
\definecolor{nord9}{HTML}{81a1c1}
\definecolor{nord10}{HTML}{5e81ac}
\definecolor{nord11}{HTML}{bf616a}
\definecolor{nord12}{HTML}{d08770}
\definecolor{nord13}{HTML}{ebcb8b}
\definecolor{nord14}{HTML}{a3be8c}
\definecolor{nord15}{HTML}{b48ead}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% MARGINS %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% Definition of margin lengths
\newlength{\tmargin} \setlength{\tmargin}{1.5cm}
\newlength{\bmargin} \setlength{\bmargin}{2cm}
\newlength{\lmargin} \setlength{\lmargin}{2.5cm}
\newlength{\rmargin} \setlength{\rmargin}{2.5cm}
\addtolength{\tmargin}{\headheight}
\addtolength{\tmargin}{\headsep}
\addtolength{\bmargin}{\footskip}
% \addtolength{\lmargin}{\oddsidemargin}
% \addtolength{\rmargin}{\evensidemargin}
%% memoir specific commands to set margins and blocks
\setulmarginsandblock{\tmargin}{\bmargin}{*}
\setlrmarginsandblock{\lmargin}{\rmargin}{*}
\checkandfixthelayout

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% SECTION NUMBERING %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\renewcommand{\thesection}{\arabic{section}}
\setsecnumdepth{subsection}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% PAGE NUMBERING %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\makeoddfoot{plain}{}{}{\thepage}
\makepagestyle{pter}
\makeoddhead{pter}{\itshape\leftmark}{}{}
\makeevenhead{pter}{}{}{\itshape\leftmark}
\makeoddfoot{pter}{}{}{\thepage}
\makeevenfoot{pter}{\thepage}{}{}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% TITLE FIELDS %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% - Nom administratif de l'établissement de soutenance
\DeclareRobustCommand*\institute[1]{%
	\gdef\@institute{#1}
}
\institute{}

%% - Nom de l'école doctorale
\DeclareRobustCommand*\phdschool[1]{%
	\gdef\@phdschool{#1}
}
\phdschool{}

%% - Champs disciplinaire
\DeclareRobustCommand*\researchfield[1]{%
	\gdef\@researchfield{#1}
}
\researchfield{}

%% - NOMS & Prénoms des directeurs de thèse
\DeclareRobustCommand*\supervisor[1]{%
	\gdef\@supervisor{#1}
}
\supervisor{}

%% - NOM Administratif du laboratoire ou Unité de Recherche
\DeclareRobustCommand*\laboratory[1]{%
	\gdef\@laboratory{#1}
}
\laboratory{}

%% NOMS et Prénoms membres du jury
\DeclareRobustCommand\jury[1]{%
	\gdef\@jury{#1}
}
\jury{}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% TITLE PAGE REDEFINITION %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%% Conditionals to make some renderings optional %%%%%%%%%%%%%%%%%%%%%%%
\newif\ifpublish % This if macro prints defence/viva date
\newif\ifjury % This if macro prints jury

%%%%%%%%%% DevMacro to render block frames in you document %%%%%%%%%%%%%%%%%%%%%
\newif\ifdev
% \devtrue
\ifdev
	\usepackage{showframe}
\fi

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% THESIS TITLE PAGE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\renewcommand{\maketitle}{%
	\begin{titlingpage}
		\begin{center}
			\vspace*{.05\paperheight}
			{\huge\textbf{THÈSE}}

			\vspace{1em}

			{\textbf{pour l'obtention du Grade de}}

			\vspace{1em}

			\textsc{\large\bfseries Docteur de l'\@institute}

			{\footnotesize (Diplôme National - Arrêté du {\color{purple}26 Août 2022})}

			\vspace{1em}

			{\footnotesize École Doctorale~: \@phdschool}

			{\footnotesize Secteur de Recherche~: \@researchfield}

			\vspace{1em}

			{\footnotesize\textit{Présentée par}~:}

			\vspace{1mm}

			{\large\textbf{\@author}}

			\vspace{.5em}

			\par\noindent\rule{.75\textwidth}{.5pt}

			\vspace{1em}

			\parbox{.75\textwidth}{%
				\centering
				\large\textbf{\@title}}

			\vspace{1em}

			\par\noindent\rule{.5\textwidth}{0.6pt}

			\vspace{1em}

			{\footnotesize Sous la direction de {\bfseries\normalsize \@supervisor}}

			\vspace{1mm}

			{\footnotesize Au {\bfseries\normalsize \@laboratory}}

			\vspace{1em}

			\par\noindent\rule{.5\textwidth}{0.6pt}

			\vspace{1em}

			\ifpublish

			{\footnotesize Soutenue le {\@date\space} devant la Commission d'Examen} %% Keeping \@date between brackets prevents font/colour bleeding

			\fi

			\vspace{2em}

			\ifjury

			{\large\bfseries\underline{JURY}}

			\vfill

			% {\raggedright{\bfseries\normalsize {\@jury}}}
			\bfseries\normalsize {\@jury}
			\fi
			\vspace*{0.05\paperheight}

		\end{center}
	\end{titlingpage}

}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% Chapter Style %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\newlength{\numberheight} \setlength{\numberheight}{50pt}
\newlength{\barlength} \setlength{\barlength}{25pt}

\makechapterstyle{gaide}{%
	\setlength{\afterchapskip}{40pt}
	\renewcommand*{\chapterheadstart}{\vspace*{0pt}}
	\renewcommand*{\afterchapternum}{\par\nobreak\vskip 25pt}
	\renewcommand*{\chapnamefont}{\normalfont\LARGE\flushright}
	\renewcommand*{\chapnumfont}{\normalfont\bfseries\HUGE\color{nord10}}
	\renewcommand*{\chaptitlefont}{\normalfont\HUGE\bfseries\flushright}
	\renewcommand*{\printchaptername}{%
		\chapnamefont\MakeUppercase{\@chapapp}}
	\renewcommand*{\chapternamenum}{}
	\setlength{\beforechapskip}{18mm}
	\setlength{\midchapskip}{\paperwidth}
	\addtolength{\midchapskip}{-\textwidth}
	\addtolength{\midchapskip}{-\spinemargin}
	\renewcommand*{\printchapternum}{%
		\makebox[2pt][l]{\hspace{1em}%
			\resizebox{!}{\numberheight}{\chapnumfont \thechapter}%
			\hspace{0.35em}%
			\rule{\barlength}{\numberheight}%
		}
	}%
	% \renewcommand*{\printchaptertitle}{%
	%   \color{nord0}
	%   \chaptitlefont}
	\renewcommand*{\afterchaptertitle}{\clearpage}
}
