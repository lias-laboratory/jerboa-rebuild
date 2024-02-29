\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{LiasThesis}[2024/02/28 Lias Thesis class]
%% Classe d'origine
\LoadClass[a4paper,12pt]{memoir}

%% memoir specific command manage margin and blocks
\setulmarginsandblock{1.5cm}{2cm}{*}
\setlrmarginsandblock{2.5cm}{2.5cm}{*}
\checkandfixthelayout

%% Packages requis pour le fonctionnement de LiasThesis
\RequirePackage[utf8]{inputenc} %% On veut tous les caractères et diactriques
\RequirePackage[T1]{fontenc} %% T1 est suffisament complet pour avoir textbf + textsc
\RequirePackage{tabto} %% Pour aligner arbitrairement du text
\RequirePackage{xcolor}

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

%% Définition de macros pou certains affichages
\newif\ifpublish % This if macro prints defence/viva date
\newif\ifjury % This if macro prints jury
\newif\ifdev
% \devtrue

\ifdev
	\usepackage{showframe}
\fi

% \ifpdftex
%   % Maybe issue pdftex logs
% \fi
% \ifluatex
%   % Maybe issue luatex logs
% \fi
% \ifxetex
%   % Maybe issue xetex logs
% \fi

%% Définitions des couleurs

\renewcommand{\maketitle}{%
	\begin{titlingpage}
		\begin{center}
			\vspace*{.05\paperheight}
			{\huge\textbf{THÈSE}}

			\vspace{1em}

			{\textbf{pour l'obtention du Grade de}}

			\vspace{1em}

			\textsc{\large\bfseries Docteur de l'\@institute}

			{\footnotesize (Diplôme National - Arrêté du 26 Août 2022)}

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

			{\footnotesize Au sein du {\bfseries\normalsize \@laboratory}}

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

			{\raggedright{\bfseries\normalsize {\@jury}}}
			\fi
			\vspace*{0.05\paperheight}

		\end{center}
	\end{titlingpage}

}
