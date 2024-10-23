\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{LiasThesis}[2024/02/28 Lias Thesis class]


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% MEMOIR EXTENSION FOR THESIS TEMPLATE %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\LoadClass[a4paper,12pt,twoside,openright,final]{memoir}
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% REQUIRED PACKAGES FOR LIASTHESIS %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\RequirePackage[utf8]{inputenc} %% On veut tous les caractères et diacritiques
\RequirePackage[T1]{fontenc} %% T1 est suffisamment complet pour avoir textbf + textsc
\RequirePackage{graphicx} %% À titre personnel
\RequirePackage{xcolor} %
\RequirePackage{tabto} %% Pour aligner arbitrairement du text
\RequirePackage{translator} %
\RequirePackage{tikz} %
\RequirePackage[french]{minitoc} %
\RequirePackage{tgtermes} %
\RequirePackage{datetime2}
% \RequirePackage[scaled]{helvet}
% \renewcommand\familydefault{\sfdefault}

\usetikzlibrary{positioning,calc}

\usedictionary{translator-basic-dictionnary}
\newtranslation[to = french]{THESIS}{THÈSE}
\newtranslation[to = english]{THÈSE}{THESIS}
\newtranslation[to = french]{Submitted for the degree of}{Pour l'obtention du Grade de}
\newtranslation[to = english]{Pour l'obtention du Grade de}{Submitted for the degree of}
\newtranslation[to = french]{Doctor of Philosophy from~}{Docteur de l'}
\newtranslation[to = english]{Docteur de l'}{Doctor of Philosophy from~}
\newtranslation[to = french]{National Degree}{Diplôme National}
\newtranslation[to = english]{Diplôme National}{National Degree}
\newtranslation[to = french]{Decree of}{Arrêté du}
\newtranslation[to = english]{Arrêté du}{Decree of}
\newtranslation[to = french]{Modified by Decree of}{Modifié par l'Arrêté du}
\newtranslation[to = english]{Modifié par l'Arrêté du}{Modified by Decree of}
\newtranslation[to = french]{May 25, 2016}{25 mai 2016}
\newtranslation[to = english]{25 mai 2016}{May 25, 2016}
\newtranslation[to = french]{August 26, 2022}{26 Août 2022}
\newtranslation[to = english]{26 Août 2022}{August 26, 2022}
\newtranslation[to = french]{Doctoral School}{École Doctorale}
\newtranslation[to = english]{École Doctorale}{Doctoral School}
\newtranslation[to = french]{Field of Research}{Secteur de Recherche}
\newtranslation[to = english]{Secteur de Recherche}{Field of Research}
\newtranslation[to = french]{Defended by}{Présentée par}
\newtranslation[to = english]{Présentée par}{Defended by}
\newtranslation[to = french]{Doctoral Advisor(s)}{Directeur(s) de thèse}
\newtranslation[to = english]{Directeur(s) de thèse}{Doctoral Advisor(s)}
\newtranslation[to = french]{Co-advisors}{Co-encadrants}
\newtranslation[to = english]{Co-encadrant(s)}{Co-advisor(s)}
\newtranslation[to = french]{Under the supervision of}{Sous la direction de}
\newtranslation[to = english]{Sous la direction de}{Under the supervision of}
\newtranslation[to = french]{At}{Au}
\newtranslation[to = english]{Au}{At}
\newtranslation[to = french]{Defended on}{Soutenue le}
\newtranslation[to = english]{Soutenue le}{Defended on}
\newtranslation[to = french]{before the examination committee}{devant la Commission d'Examen}
\newtranslation[to = english]{devant la Commission d'Examen}{before the examination committee}
\newtranslation[to = french]{Examination Board}{Jury}
\newtranslation[to = english]{Jury}{Examination Board}
\newtranslation[to = french]{Abstract:}{Résumé~:}
\newtranslation[to = english]{Résumé~:}{Abstract:}

% \ifpdftex
%   % Maybe issue pdftex logs
% \fi
% \ifluatex % Maybe issue luatex logs
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
%% Adding some length to have *real* margins both on top and bottom
\addtolength{\tmargin}{\headheight}
\addtolength{\tmargin}{\headsep}
\addtolength{\bmargin}{\footskip}
%% memoir specific commands to set margins and blocks
\setulmarginsandblock{\tmargin}{\bmargin}{*}
\setlrmarginsandblock{\lmargin}{\rmargin}{*}
\checkandfixthelayout


\newcommand{\TitleFrame}{}

\DeclareOption{framed}{%
	\renewcommand{\TitleFrame}{%
		\begin{tikzpicture}[remember picture, overlay]
			\draw[line width = 1pt]
			($(current page.north west) + (\lmargin-1em,-\tmargin)$) rectangle
			($(current page.south east) + (-\rmargin+1em,\bmargin-1em)$);
		\end{tikzpicture}
	}
}

\newif\ifdev

\DeclareOption{framepage}{%
	\devtrue
}

\ProcessOptions\relax


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% SECTION NUMBERING %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% \renewcommand{\@chapter}{\addcontentsline{toc}{chapter}{\protect\numerline{\thechapter}#1}}%
\renewcommand{\thesection}{\arabic{section}}
\setsecnumdepth{subsubsection}
\setcounter{tocdepth}{3}

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
\DeclareRobustCommand*\doctoralschool[1]{%
	\gdef\@doctoralschool{#1}
}
\doctoralschool{}

%% - Champs disciplinaire
\DeclareRobustCommand*\researchfield[1]{%
	\gdef\@researchfield{#1}
}
\researchfield{}

%% - NOMS & Prénoms des directeurs de thèse
\DeclareRobustCommand*\phddirector[1]{%
	\gdef\@phddirector{#1}
}
\phddirector{}

\DeclareRobustCommand*\cosupervisors[1]{%
	\gdef\@cosupervisors{#1}
}
\cosupervisors{}

%% - NOM Administratif du laboratoire ou Unité de Recherche
\DeclareRobustCommand*\laboratory[1]{%
	\gdef\@laboratory{#1}
}
\laboratory{}

%% NOMS et Prénoms membres du comittee
\DeclareRobustCommand\examboard[1]{%
	\gdef\@examboard{#1}
}
\examboard{}

%% Logo établissement
\DeclareRobustCommand*\titlelogoleft[1]{%
	\gdef\@titlelogoleft{#1}
}
\titlelogoleft{}

%% Logo laboratoire
\DeclareRobustCommand*\titlelogoright[1]{%
	\gdef\@titlelogoright{#1}
}
\titlelogoright{}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% TITLE PAGE REDEFINITION %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%% Conditionals to make some renderings optional %%%%%%%%%%%%%%%%%%%%%%%
\newif\ifpublish % This if macro prints defence/viva date
\newif\ifexamboard % This if macro prints exam board

%%%%%%%%%% DevMacro to render block frames in you document %%%%%%%%%%%%%%%%%%%%%
\ifdev
	\usepackage{showframe}
\fi

%%%%%%%%%% Additional commands %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\newcommand\asteriskfill{\leavevmode\xleaders\hbox{$\ast$}\hfill\kern0pt}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% THESIS TITLE PAGE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\newcommand{\TitleLogos}{%
	\begin{tikzpicture}[overlay, remember picture]
		% if \@titlelogoleft is empty then relax evaluates to relax
		\if\relax \@titlelogoleft \relax % goes to true branch
		\else % goes to the false branch
			\node (TitleLogoLeft) at ($(current page.north west) + (1em,-1em)$)
			[below right = \tmargin and \lmargin] {
				\includegraphics[height=2cm]{\@titlelogoleft} };
		\fi
		\if\relax \@titlelogoright \relax
		\else
			\node (TitleLogoRight) at ($(current page.north east) + (-1em,-1em)$)
			[below left = \tmargin and \rmargin] {
				\includegraphics[height=2cm]{\@titlelogoright}
			};
		\fi
	\end{tikzpicture}
}


\renewcommand{\maketitle}{%
	\begin{titlingpage}
		% \fontfamily{phv}\selectfont
		\AddToHook{shipout/firstpage}{%
			\put(0,-\paperheight)
			{\TitleFrame\TitleLogos}}
		\begin{center}
			% \vspace*{.0125\paperheight}
			{{\bfseries\fontsize{22}{24}\fontfamily{ptm}\selectfont\translate{THESE}}}

			{\vspace{14.5pt}}

			{{\translate{Pour l'obtention du Grade de}}}

			\parbox[t]{\textwidth}{\fontsize{14}{15.5}\selectfont\centering\scshape\bfseries
				\translate{Docteur de l'}\@institute}\\[1mm]

			\parbox[t]{.95\textwidth}{\small\centering(\translate{Diplôme
					National} - \translate{Arrêté du} \translate{25 mai 2016}
				\translate{Modifié par l'Arrêté du} \translate{26 Août 2022})}

			\vspace{14pt}

			{ \translate{Ecole Doctorale}~:\\
				\@doctoralschool}

			\vspace{14pt}

			\parbox[t]{.7\textwidth}{ \translate{Secteur de Recherche}~:
				\@researchfield}

			\vspace{14pt}

			{{\translate{Présentée par}}~:}

			\vspace{5pt}

			\parbox[t]{.7\textwidth}{\large\centering{\@author}}

			\vspace{10pt}

			{\Large ****************************}

			\vspace{14pt}

			\parbox[t]{.75\textwidth}{\centering{\@title}}

			\vspace{14pt}

			{\Large ****************************}

			{ { \@phddirector}}

			\vspace{0.25em}

			{ { \@cosupervisors}}

			\vspace{14pt}

			{\Large ****************************}

			% \vspace{1em}

			\ifpublish
				\parbox[t]{.6\textwidth}{%
					\centering%
					{\translate{Soutenue le}} {\@date\space}}\\[1em]
				%% Keeping \@date between brackets prevents font/colour bleeding
				{\translate{devant la Commission d'Examen}}
			\fi

			\vspace{14pt}

			{\Large ****************************}

			\vspace{14pt}

			\ifexamboard

			{\Large\bfseries\scshape\underline{\translate{JURY}}}

			\vfill

			{\raggedright{\normalsize {\@examboard}}}
			\fi

		\end{center}
	\end{titlingpage}

}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%% Chapter Style %%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\newlength{\numberheight} \setlength{\numberheight}{50pt}
\newlength{\barlength} \setlength{\barlength}{25pt}

\makechapterstyle{customveelo}{%
	\setlength{\afterchapskip}{40pt}
	\renewcommand*{\chapterheadstart}{\thispagestyle{empty}\vspace*{2cm}}
	\renewcommand*{\afterchapternum}{\par\nobreak\vskip 25pt}
	\renewcommand*{\chapnamefont}{\normalfont\LARGE\flushright}
	\renewcommand*{\chapnumfont}{\fontfamily{tgtermes}\normalfont\bfseries\HUGE\color{nord10}}
	\renewcommand*{\chaptitlefont}{\normalfont\HUGE\bfseries\flushleft}
	\renewcommand*{\printchaptername}{\chapnamefont\MakeUppercase{\@chapapp}}
	\renewcommand*{\chapternamenum}{}
	\setlength{\beforechapskip}{18mm}
	\setlength{\midchapskip}{\paperwidth}
	\addtolength{\midchapskip}{-\textwidth}
	\addtolength{\midchapskip}{-\spinemargin}
	\renewcommand*{\printchapternum}{%
		\makebox[.4\numberheight][l]{\hspace{1em}%
			\resizebox{!}{\numberheight}{\chapnumfont \thechapter}%
			\hspace{0.35em}%
			\rule{1.2\barlength}{\numberheight}%
		}
	}%
	% \renewcommand*{\printchaptertitle}{%
	%   \color{nord0}
	%   \chaptitlefont}
	\renewcommand*{\afterchaptertitle}{\vfill\minitoc\vfill\clearpage}
}

\newcommand{\keywords}[1]{%
	\noindent
	\underline{\translate{Mots clés~:}} (#1)
}

\renewenvironment{abstract}{%
	\small
	\raggedright{\underline\abstractname}\vspace{.5em}\vspace{0pt}
	\list{}{%
		\setlength{\leftmargin}{0pt}%
		\setlength{\rightmargin}{0pt}%
	}%
	\item\relax}
{\endlist}

\newenvironment{resume}[1]{%

	\centering
	{\normalsize\bfseries\textsc{#1}}

	\vspace{1em}
	\renewcommand{\abstractnamefont}{\raggedright\normalfont\small\underline}
	\renewcommand{\abstractname}{\translate{Résumé~:}}
	\noindent
	\begin{abstract}
		}{%
	\end{abstract}
}

%%% Local Variables:
%%% mode: latex
%%% TeX-master: "main"
%%% End:
