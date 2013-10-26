--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.4
-- Dumped by pg_dump version 9.2.2
-- Started on 2013-10-26 12:58:53

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = common, pg_catalog;

--
-- TOC entry 2003 (class 0 OID 17439)
-- Dependencies: 170
-- Data for Name: enterprises; Type: TABLE DATA; Schema: common; Owner: postgres
--

COPY enterprises (emp_id, emp_name, emp_user) FROM stdin;
AMM874695F20	AMANDA MIGUEL MIRANDA MARTINEZ	admin
CMR568710S47	CASIMIRO MIRAMONTES RODRIGUEZ	admin
MMJ745924R59	MIGUEL MATEOS RAMIREZ	agarcia
SCI041122EI5	SERVICOS CORPORATIVOS ITC SA DE CV	admin
AGL860805G63	AGA	admin
BEHF650530676	BEH	admin
BAMJ850411K10	JOSE JAHIR BAROJAS MUÑO	admin
TAV951205C15	TAV	admin
MME020905462	MULTINA MEXICO	agarcia
XEXX010101000	PRUEBA	agarcia
BGL860805G63	Test	MMichel
\.


--
-- TOC entry 2004 (class 0 OID 17448)
-- Dependencies: 171
-- Data for Name: permissions; Type: TABLE DATA; Schema: common; Owner: postgres
--

COPY permissions (rol_id, tsk_id) FROM stdin;
ADMIN	admin.logs
ADMIN	auditor.activity
ADMIN	report.user.add
ADMIN	report.user.delete
ADMIN	report.user
ADMIN	report.user.modify
ADMIN	users.changePassword
ADMIN	users.setPassword
ADMIN	users.report.PDF
ADMIN	users.report.EXCEL
USER	report.document
USER	report.enterprise.add
USER	report.enterprise.delete
USER	report.enterprise
USER	report.enterprise.modify
USER	users.report.PDF
USER	users.setPassword
USER	users.report.EXCEL
\.


--
-- TOC entry 2005 (class 0 OID 17454)
-- Dependencies: 172
-- Data for Name: tasks; Type: TABLE DATA; Schema: common; Owner: postgres
--

COPY tasks (tsk_id, tsk_name, tsk_image, tsk_url, tsk_type) FROM stdin;
report.document	Documents	rep_doc_gen.jpg	reportDocument.task	MENU
report.enterprise	Entreprise	rep_ent_gen.jpg	reportEnterprise.task	MENU
report.user	Utilisateurs	rep_user_gen.png	reportUser.task	MENU
users.report.EXCEL	Génération Excel	\N	reportEXCEL.form	ACTION
users.report.PDF	Génération PDF	\N	reportPDF.form	ACTION
users.changePassword	Changer de Mot de Passe	\N	changePassword.form	ACTION
report.enterprise.add	Ajout d'une entreprise	\N	addEnterprise.form	ACTION
report.enterprise.delete	Suppression d'une entreprise	\N	deleteEnterprise.form	ACTION
report.enterprise.modify	Modifier une entreprise	\N	modifyEnterprise.form	ACTION
report.user.add	Ajout d'un utilisateur	\N	addUser.form	ACTION
report.user.delete	Supprimer un utilisateur	\N	deleteUser.form	ACTION
report.user.modify	Modifier un utilisateur	\N	modifyUser.form	ACTION
users.setPassword	Rétablir un mot de passe	\N	setPassword.form	ACTION
admin.logs	Logs	\r	log.task	MENU
auditor.activity	Rapport d'activité	task_auditoria.png	activity.task	MENU
\.


--
-- TOC entry 2006 (class 0 OID 17463)
-- Dependencies: 173
-- Data for Name: users; Type: TABLE DATA; Schema: common; Owner: postgres
--

COPY users (user_id, user_name, user_email, user_pwd, user_status, user_pwdreset, user_type) FROM stdin;
agarcia	Ángel García Alcántara	michel.messak@gmail.com	3cd6e84a34793dabc32d3748bc890aa88cd1e018d3d86170c0565248f9ab0d49	A	f	USER
admin	Admin	agarcia@itcomplements.com	8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918	A	f	ADMIN
\.


SET search_path = period, pg_catalog;

--
-- TOC entry 2007 (class 0 OID 17471)
-- Dependencies: 174
-- Data for Name: activities; Type: TABLE DATA; Schema: period; Owner: postgres
--

COPY activities (act_id, user_id, act_date, act_time, act_description, act_ip_address) FROM stdin;
8706	admin	2013-10-16	15:44:01.62	Consulta a usuarios	127.0.0.1
8764	agarcia	2013-10-18	12:04:24.746	Inicio de sesión	127.0.0.1
8765	agarcia	2013-10-18	12:05:24.956	Consulta de reporte de empresas	127.0.0.1
8807	admin	2013-10-20	10:47:30.495	Inicio de sesión	127.0.0.1
8816	agarcia	2013-10-21	16:04:03.822	Début de session	127.0.0.1
8817	agarcia	2013-10-21	16:04:14.47	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
8818	agarcia	2013-10-21	16:05:01.16	Consultation des entreprises	127.0.0.1
8819	agarcia	2013-10-21	16:06:30.509	Consultation des entreprises	127.0.0.1
8820	agarcia	2013-10-21	16:06:34.631	Entreprise EDY860510S06 supprimée	127.0.0.1
8821	agarcia	2013-10-21	16:06:37.325	Consultation des entreprises	127.0.0.1
8822	agarcia	2013-10-21	16:06:43.794	Entreprise HYM091130QYA Modifiée	127.0.0.1
8823	agarcia	2013-10-21	16:06:46.432	Consultation des entreprises	127.0.0.1
8824	agarcia	2013-10-21	16:06:49.844	Entreprise HYM091130QYA supprimée	127.0.0.1
8825	agarcia	2013-10-21	16:06:52.112	Consultation des entreprises	127.0.0.1
8826	admin	2013-10-21	16:07:23.046	Début de session	127.0.0.1
8827	admin	2013-10-21	16:07:49.659	Consultation du rapport d'activité	127.0.0.1
9038	agarcia	2013-10-22	23:02:57.583	Début de session	127.0.0.1
9039	agarcia	2013-10-22	23:03:59.906	Le mot de passe de l'utilisateur agarcia a été actualisé	127.0.0.1
9040	agarcia	2013-10-22	23:04:15.157	Le mot de passe de l'utilisateur agarcia a été actualisé	127.0.0.1
9041	agarcia	2013-10-22	23:04:51.382	Début de session	127.0.0.1
9042	agarcia	2013-10-22	23:05:02.8	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9043	agarcia	2013-10-22	23:05:12.532	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9044	agarcia	2013-10-22	23:07:14.117	Consultation des documents de l'entreprise MMJ745924R59	127.0.0.1
9045	agarcia	2013-10-22	23:07:24.199	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9046	agarcia	2013-10-22	23:07:28.205	Consulta de Documento 94456b97-8496-4659-8d21-c563bf1d407e de la empresa XEXX010101000	127.0.0.1
9047	agarcia	2013-10-22	23:07:33.696	Consultation des entreprises	127.0.0.1
9048	agarcia	2013-10-22	23:07:54.876	L'entreprise EDY860510S03 a été ajoutée	127.0.0.1
9049	agarcia	2013-10-22	23:07:57.587	Consultation des entreprises	127.0.0.1
9050	agarcia	2013-10-22	23:08:02.929	Entreprise EDY860510S03 Modifiée	127.0.0.1
9051	agarcia	2013-10-22	23:08:05.462	Consultation des entreprises	127.0.0.1
9052	agarcia	2013-10-22	23:08:08.47	Entreprise EDY860510S03 supprimée	127.0.0.1
9053	agarcia	2013-10-22	23:08:10.974	Consultation des entreprises	127.0.0.1
9054	admin	2013-10-22	23:08:24.864	Début de session	127.0.0.1
9055	admin	2013-10-22	23:08:39.795	Le mot de passe de l'utilisateur admin a été actualisé	127.0.0.1
9070	admin	2013-10-24	12:13:46.225	Début de session	127.0.0.1
9071	admin	2013-10-24	12:14:03.372	Consultation du rapport d'activité	127.0.0.1
9072	admin	2013-10-24	12:14:10.87	Consultation des utilisateurs	127.0.0.1
9073	admin	2013-10-24	12:14:16.449	Utilisateurs avec PDF	127.0.0.1
9074	agarcia	2013-10-24	13:32:53.323	Début de session	127.0.0.1
9075	agarcia	2013-10-24	13:33:05.333	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9153	agarcia	2013-10-25	12:02:37.327	Début de session	127.0.0.1
8707	admin	2013-10-16	15:46:38.18	Consulta a usuarios	127.0.0.1
8708	admin	2013-10-16	15:46:44.131	Acceso a tarea Permitida: altaUsuario.form	127.0.0.1
8709	admin	2013-10-16	15:46:44.134	Acceso a tarea PErmitida: altaUsuario.form	127.0.0.1
8710	admin	2013-10-16	15:47:11.002	Usuario MMichel eliminado	127.0.0.1
8711	admin	2013-10-16	15:47:13.445	Consulta a usuarios	127.0.0.1
8766	agarcia	2013-10-18	12:07:41.027	Inicio de sesión	127.0.0.1
8767	agarcia	2013-10-18	12:07:48.155	Consulta de reporte de empresas	127.0.0.1
8768	agarcia	2013-10-18	12:07:48.465	Consulta de reporte de empresas	127.0.0.1
8808	admin	2013-10-20	10:48:04.922	Consulta de actividades	127.0.0.1
8809	admin	2013-10-20	10:48:33.943	Consulta a usuarios	127.0.0.1
8810	admin	2013-10-20	10:49:21.075	Consulta de actividades	127.0.0.1
8811	agarcia	2013-10-20	10:51:15.932	Inicio de sesión	127.0.0.1
8812	agarcia	2013-10-20	10:51:22.803	Consulta de los documentos de la empresa EDY860510S06	127.0.0.1
8813	agarcia	2013-10-20	10:51:31.922	Consulta de los documentos de la empresa XEXX010101000	127.0.0.1
8828	agarcia	2013-10-21	16:29:53.2	Début de session	127.0.0.1
8829	agarcia	2013-10-21	16:29:55.66	Consultation des entreprises	127.0.0.1
8830	agarcia	2013-10-21	16:30:02.018	Consultation des entreprises	127.0.0.1
9056	admin	2013-10-22	23:10:51.469	Début de session	127.0.0.1
9057	admin	2013-10-22	23:11:16.648	Consultation du rapport d'activité	127.0.0.1
9058	admin	2013-10-22	23:11:28.92	Consulta a usuarios	127.0.0.1
9059	admin	2013-10-22	23:11:50.096	Accès à la tache permise : modifyUser.form	127.0.0.1
9060	admin	2013-10-22	23:11:50.097	Acceso a tarea PErmitida: modifyUser.form	127.0.0.1
9061	admin	2013-10-22	23:11:56.231	Usuario Michel modificado	127.0.0.1
9062	admin	2013-10-22	23:11:57.553	Consulta a usuarios	127.0.0.1
9063	admin	2013-10-22	23:12:00.208	Accès à la tache permise : addUser.form	127.0.0.1
9064	admin	2013-10-22	23:12:00.21	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9065	admin	2013-10-22	23:12:04.08	Consulta a usuarios	127.0.0.1
9066	admin	2013-10-22	23:12:06.367	Accès à la tache permise : deleteUser.form	127.0.0.1
9067	admin	2013-10-22	23:12:06.371	Acceso a tarea PErmitida: deleteUser.form	127.0.0.1
9068	admin	2013-10-22	23:12:07.854	Usuario Michel eliminado	127.0.0.1
9069	admin	2013-10-22	23:12:10.419	Consulta a usuarios	127.0.0.1
9076	agarcia	2013-10-24	13:46:07.232	Début de session	127.0.0.1
9077	agarcia	2013-10-24	13:46:19.365	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9078	agarcia	2013-10-24	13:46:20.066	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9154	agarcia	2013-10-25	12:17:53.186	Début de session	127.0.0.1
8769	agarcia	2013-10-18	12:21:50.376	Inicio de sesión	127.0.0.1
8770	agarcia	2013-10-18	12:21:54.959	Consulta de reporte de empresas	127.0.0.1
8771	agarcia	2013-10-18	12:22:16.76	Empresa HYM091130QYD agregada	127.0.0.1
8772	agarcia	2013-10-18	12:22:20.632	Consulta de reporte de empresas	127.0.0.1
8773	agarcia	2013-10-18	12:22:37.528	Empresa HYM091130QYD eliminada	127.0.0.1
8774	agarcia	2013-10-18	12:22:40.884	Consulta de reporte de empresas	127.0.0.1
8814	admin	2013-10-20	11:02:51.366	Inicio de sesión	127.0.0.1
8815	admin	2013-10-20	11:02:57.692	Consulta de actividades	127.0.0.1
8831	admin	2013-10-21	16:36:43.217	Début de session	127.0.0.1
8832	admin	2013-10-21	16:36:46.953	Consulta a usuarios	127.0.0.1
8833	admin	2013-10-21	16:36:49.611	Intento Accesar a una tarea no permitida: addUser.form	127.0.0.1
8834	admin	2013-10-21	16:36:52.579	Intento Accesar a una tarea no permitida: addUser.form	127.0.0.1
8835	admin	2013-10-21	16:37:10.279	Intento Accesar a una tarea no permitida: addUser.form	127.0.0.1
8836	admin	2013-10-21	16:37:20.464	Début de session	127.0.0.1
8837	admin	2013-10-21	16:37:22.459	Consulta a usuarios	127.0.0.1
8838	admin	2013-10-21	16:37:23.692	Acceso a tarea Permitida: addUser.form	127.0.0.1
8839	admin	2013-10-21	16:37:23.71	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9079	agarcia	2013-10-24	13:49:01.132	Début de session	127.0.0.1
9080	agarcia	2013-10-24	13:49:08.393	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
8775	agarcia	2013-10-18	13:28:51.587	Inicio de sesión	127.0.0.1
8776	agarcia	2013-10-18	13:29:10.727	Consulta de reporte de empresas	127.0.0.1
8777	agarcia	2013-10-18	13:31:00.193	Empresa EDY860510S04 agregada	127.0.0.1
8840	admin	2013-10-21	16:38:11.944	Début de session	127.0.0.1
8841	admin	2013-10-21	16:38:13.59	Consulta a usuarios	127.0.0.1
8842	admin	2013-10-21	16:38:15.596	Acceso a tarea Permitida: addUser.form	127.0.0.1
8843	admin	2013-10-21	16:38:15.6	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9081	agarcia	2013-10-24	13:49:44.151	Début de session	127.0.0.1
9082	agarcia	2013-10-24	13:49:51.517	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
8778	agarcia	2013-10-18	13:39:20.395	Inicio de sesión	127.0.0.1
8779	agarcia	2013-10-18	13:39:32.735	Consulta de reporte de empresas	127.0.0.1
8780	agarcia	2013-10-18	13:39:55.191	Empresa EDY860510S06 agregada	127.0.0.1
8781	agarcia	2013-10-18	13:39:58.47	Consulta de reporte de empresas	127.0.0.1
8782	agarcia	2013-10-18	13:40:07.38	Consulta de reporte de empresas	127.0.0.1
8783	agarcia	2013-10-18	13:40:12.787	Empresa EDY860510S04 eliminada	127.0.0.1
8784	agarcia	2013-10-18	13:40:15.398	Consulta de reporte de empresas	127.0.0.1
8785	agarcia	2013-10-18	13:40:25.991	Consulta de reporte de empresas	127.0.0.1
8786	admin	2013-10-18	13:40:44.171	Inicio de sesión	127.0.0.1
8787	admin	2013-10-18	13:41:11.19	Consulta a la bitácora C:/ITC/FC4/Logs\\2013\\10\\fc4.20131015.log del día 2013/10/15	127.0.0.1
8844	admin	2013-10-21	16:39:00.46	Début de session	127.0.0.1
8845	admin	2013-10-21	16:39:03.079	Consulta a usuarios	127.0.0.1
8846	admin	2013-10-21	16:39:04.99	Acceso a tarea Permitida: addUser.form	127.0.0.1
8847	admin	2013-10-21	16:39:04.996	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8848	admin	2013-10-21	16:39:09.871	Acceso a tarea Permitida: addUser.form	127.0.0.1
8849	admin	2013-10-21	16:39:09.878	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9083	agarcia	2013-10-24	13:51:34.741	Début de session	127.0.0.1
9084	agarcia	2013-10-24	13:51:43.788	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9085	agarcia	2013-10-24	13:52:01.752	Consultation des documents de l'entreprise MME020905462	127.0.0.1
8788	agarcia	2013-10-18	15:50:02.109	Inicio de sesión	127.0.0.1
8789	agarcia	2013-10-18	15:50:11.285	Consulta de reporte de empresas	127.0.0.1
8790	agarcia	2013-10-18	15:50:43.724	Consulta de los documentos de la empresa XEXX010101000	127.0.0.1
8850	admin	2013-10-21	16:43:36.376	Début de session	127.0.0.1
8851	admin	2013-10-21	16:43:38.507	Consulta a usuarios	127.0.0.1
8852	admin	2013-10-21	16:43:40.254	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8853	admin	2013-10-21	16:43:41.904	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8854	admin	2013-10-21	16:43:42.583	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8855	admin	2013-10-21	16:43:43.071	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8856	admin	2013-10-21	16:43:43.214	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8857	admin	2013-10-21	16:43:43.368	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8858	admin	2013-10-21	16:43:43.522	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8859	admin	2013-10-21	16:43:43.665	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8860	admin	2013-10-21	16:43:43.819	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8861	admin	2013-10-21	16:43:43.962	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8862	admin	2013-10-21	16:43:44.117	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8863	admin	2013-10-21	16:43:44.258	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8864	admin	2013-10-21	16:43:44.413	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8865	admin	2013-10-21	16:43:44.556	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8866	admin	2013-10-21	16:43:58.779	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
8867	admin	2013-10-21	16:44:01.268	Consulta a usuarios	127.0.0.1
8868	admin	2013-10-21	16:44:02.488	Intento Accesar a una tarea no permitida: altaUsuario.form	127.0.0.1
9086	agarcia	2013-10-24	13:53:57.217	Début de session	127.0.0.1
9087	agarcia	2013-10-24	13:54:05.725	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
8729	admin	2013-10-16	16:12:28.903	Inicio de sesión	127.0.0.1
8730	admin	2013-10-16	16:12:31.33	Consulta a usuarios	127.0.0.1
8731	admin	2013-10-16	16:12:37.688	Consulta de reporte de empresas	127.0.0.1
8732	admin	2013-10-16	16:15:55.176	Inicio de sesión	127.0.0.1
8733	admin	2013-10-16	16:15:56.681	Consulta a usuarios	127.0.0.1
8734	admin	2013-10-16	16:16:00.316	Acceso a tarea Permitida: eliminaUsuario.form	127.0.0.1
8735	admin	2013-10-16	16:16:00.318	Acceso a tarea PErmitida: eliminaUsuario.form	127.0.0.1
8736	admin	2013-10-16	16:16:01.972	Usuario MMichel eliminado	\N
8737	admin	2013-10-16	16:16:04.518	Consulta a usuarios	127.0.0.1
8738	agarcia	2013-10-16	16:21:33.657	Inicio de sesión	127.0.0.1
8739	agarcia	2013-10-16	16:22:09.213	Inicio de sesión	127.0.0.1
8740	agarcia	2013-10-16	16:23:21.245	Inicio de sesión	127.0.0.1
8791	agarcia	2013-10-18	17:10:28.131	Inicio de sesión	127.0.0.1
8792	agarcia	2013-10-18	17:10:47.184	Consulta de reporte de empresas	127.0.0.1
8793	agarcia	2013-10-18	17:10:54.059	Empresa EDY860510S06 modificada	127.0.0.1
8794	agarcia	2013-10-18	17:10:56.799	Consulta de reporte de empresas	127.0.0.1
8869	admin	2013-10-21	16:49:33.149	Début de session	127.0.0.1
8870	admin	2013-10-21	16:49:35.688	Consulta a usuarios	127.0.0.1
8871	admin	2013-10-21	16:49:37.818	Acceso a tarea Permitida: addUser.form	127.0.0.1
8872	admin	2013-10-21	16:49:37.828	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8873	admin	2013-10-21	16:49:45.433	Acceso a tarea Permitida: addUser.form	127.0.0.1
8874	admin	2013-10-21	16:49:45.44	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8875	admin	2013-10-21	16:49:46.015	Acceso a tarea Permitida: addUser.form	127.0.0.1
8876	admin	2013-10-21	16:49:46.082	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8877	admin	2013-10-21	16:49:46.585	Acceso a tarea Permitida: addUser.form	127.0.0.1
8878	admin	2013-10-21	16:49:46.587	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9088	admin	2013-10-24	14:03:09.77	Début de session	127.0.0.1
8741	admin	2013-10-16	16:28:01.582	Inicio de sesión	127.0.0.1
8742	admin	2013-10-16	16:28:22.156	Consulta a usuarios	127.0.0.1
8743	agarcia	2013-10-16	16:28:52.247	Inicio de sesión	127.0.0.1
8795	admin	2013-10-18	17:32:21.687	Inicio de sesión	127.0.0.1
8796	admin	2013-10-18	17:34:06.756	Consulta a usuarios	127.0.0.1
8797	admin	2013-10-18	17:34:25.634	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8798	admin	2013-10-18	17:34:25.637	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8799	admin	2013-10-18	17:35:30.48	Usuario agarcia modificado	127.0.0.1
8879	admin	2013-10-21	16:50:19.427	Début de session	127.0.0.1
8880	admin	2013-10-21	16:50:22.036	Consulta a usuarios	127.0.0.1
8881	admin	2013-10-21	16:50:25.22	Acceso a tarea Permitida: addUser.form	127.0.0.1
8882	admin	2013-10-21	16:50:25.233	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9089	agarcia	2013-10-24	14:10:16.801	Début de session	127.0.0.1
9090	agarcia	2013-10-24	14:10:21.953	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9091	agarcia	2013-10-24	14:13:47.66	Consultation des documents de l'entreprise MME020905462	127.0.0.1
8744	agarcia	2013-10-16	16:30:06.857	Inicio de sesión	127.0.0.1
8800	agarcia	2013-10-18	17:42:31.747	Inicio de sesión	127.0.0.1
8801	admin	2013-10-18	17:42:47.673	Inicio de sesión	127.0.0.1
8802	admin	2013-10-18	17:43:10.438	Consulta a usuarios	127.0.0.1
8803	admin	2013-10-18	17:43:15.688	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8804	admin	2013-10-18	17:43:15.732	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8805	admin	2013-10-18	17:43:24.826	Usuario agarcia modificado	127.0.0.1
8806	admin	2013-10-18	17:43:26.288	Consulta a usuarios	127.0.0.1
8883	admin	2013-10-21	16:57:11.113	Début de session	127.0.0.1
8884	admin	2013-10-21	16:57:13.685	Consulta a usuarios	127.0.0.1
8885	admin	2013-10-21	16:57:15.628	Acceso a tarea Permitida: addUser.form	127.0.0.1
8886	admin	2013-10-21	16:57:15.635	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8887	admin	2013-10-21	16:57:46.292	Utilisateur Michel Ajouté	127.0.0.1
8888	admin	2013-10-21	16:57:49.088	Consulta a usuarios	127.0.0.1
8889	admin	2013-10-21	16:57:53.083	Acceso a tarea Permitida: eliminaUsuario.form	127.0.0.1
8890	admin	2013-10-21	16:57:53.09	Acceso a tarea PErmitida: eliminaUsuario.form	127.0.0.1
8891	admin	2013-10-21	16:57:54.429	Usuario Michel eliminado	127.0.0.1
8892	admin	2013-10-21	16:57:56.821	Consulta a usuarios	127.0.0.1
9092	agarcia	2013-10-24	14:16:10.784	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9093	agarcia	2013-10-24	14:16:24.102	Consultation des entreprises	127.0.0.1
9094	agarcia	2013-10-24	14:18:00.067	Consultation des entreprises	127.0.0.1
9095	agarcia	2013-10-24	14:19:08.651	Consultation des entreprises	127.0.0.1
9096	agarcia	2013-10-24	14:20:56.6	Consultation des entreprises	127.0.0.1
9097	agarcia	2013-10-24	14:21:24.401	L'entreprise EDY860510S04 a été ajoutée	127.0.0.1
9098	agarcia	2013-10-24	14:22:03.276	Début de session	127.0.0.1
9099	agarcia	2013-10-24	14:22:31.615	Consultation des entreprises	127.0.0.1
9100	agarcia	2013-10-24	14:22:40.526	Entreprise EDY860510S04 supprimée	127.0.0.1
8745	agarcia	2013-10-16	16:32:42.28	Inicio de sesión	127.0.0.1
8893	agarcia	2013-10-21	17:05:18.309	Début de session	127.0.0.1
8894	agarcia	2013-10-21	17:05:57.328	Début de session	127.0.0.1
8895	agarcia	2013-10-21	17:06:09.814	Contraseña de usuario agarcia actualizada	127.0.0.1
8896	agarcia	2013-10-21	17:06:20.973	Contraseña de usuario agarcia actualizada	127.0.0.1
9101	agarcia	2013-10-24	14:24:52.919	Début de session	127.0.0.1
9102	agarcia	2013-10-24	14:25:00.393	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9103	agarcia	2013-10-24	14:25:08.702	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9104	agarcia	2013-10-24	14:25:14.679	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9105	agarcia	2013-10-24	14:25:21.967	Consultation des documents de l'entreprise MMJ745924R59	127.0.0.1
9106	agarcia	2013-10-24	14:25:27.212	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9107	agarcia	2013-10-24	14:25:40.822	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9108	agarcia	2013-10-24	14:25:46.228	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9109	agarcia	2013-10-24	14:26:25.088	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9110	agarcia	2013-10-24	14:27:27.56	Consultation des entreprises	127.0.0.1
9111	agarcia	2013-10-24	14:27:37.838	Entreprise MME020905462 Modifiée	127.0.0.1
8746	agarcia	2013-10-16	16:37:51.099	Inicio de sesión	127.0.0.1
8897	admin	2013-10-21	17:11:04.827	Début de session	127.0.0.1
9112	agarcia	2013-10-24	14:29:26.986	Début de session	127.0.0.1
9113	agarcia	2013-10-24	14:29:30.421	Consultation des entreprises	127.0.0.1
9114	agarcia	2013-10-24	14:29:44.092	L'entreprise EDY860510S04 a été ajoutée	127.0.0.1
8747	agarcia	2013-10-16	19:16:35.837	Inicio de sesión	127.0.0.1
8898	admin	2013-10-21	17:12:04.847	Début de session	127.0.0.1
8899	admin	2013-10-21	17:12:22.409	Contraseña de usuario admin actualizada	127.0.0.1
8900	admin	2013-10-21	17:12:25.498	Consulta a usuarios	127.0.0.1
8901	admin	2013-10-21	17:12:28.512	Acceso a tarea Permitida: eliminaUsuario.form	127.0.0.1
8902	admin	2013-10-21	17:12:28.519	Acceso a tarea PErmitida: eliminaUsuario.form	127.0.0.1
8903	admin	2013-10-21	17:12:30.75	Consulta a usuarios	127.0.0.1
8904	admin	2013-10-21	17:12:31.93	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8905	admin	2013-10-21	17:12:31.934	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
9115	agarcia	2013-10-24	14:40:21.645	Début de session	127.0.0.1
9116	agarcia	2013-10-24	14:40:26.01	Consultation des entreprises	127.0.0.1
9117	agarcia	2013-10-24	14:40:44.122	L'entreprise EDY860510S05 a été ajoutée	127.0.0.1
9118	agarcia	2013-10-24	14:40:46.978	Consultation des entreprises	127.0.0.1
9119	agarcia	2013-10-24	14:41:29.254	Entreprise EDY860510S04 supprimée	127.0.0.1
9120	agarcia	2013-10-24	14:41:32.051	Consultation des entreprises	127.0.0.1
9121	agarcia	2013-10-24	14:42:00.179	Consultation des entreprises	127.0.0.1
9122	agarcia	2013-10-24	14:42:13.23	Entreprise EDY860510S05 Modifiée	127.0.0.1
9123	agarcia	2013-10-24	14:42:16.028	Consultation des entreprises	127.0.0.1
9124	agarcia	2013-10-24	14:42:26.949	Entreprise EDY860510S05 supprimée	127.0.0.1
9125	agarcia	2013-10-24	14:42:29.117	Consultation des entreprises	127.0.0.1
9126	agarcia	2013-10-24	14:44:48.442	Consultation des entreprises	127.0.0.1
9127	agarcia	2013-10-24	14:45:02.153	Début de session	127.0.0.1
8748	agarcia	2013-10-16	19:18:07.443	Inicio de sesión	127.0.0.1
8749	agarcia	2013-10-16	19:18:26.743	Contraseña de usuario agarcia actualizada	127.0.0.1
8750	agarcia	2013-10-16	19:19:28.907	Inicio de sesión	127.0.0.1
8906	agarcia	2013-10-21	17:19:57.189	Contraseña de usuario agarcia actualizada	127.0.0.1
8907	agarcia	2013-10-21	17:19:57.257	Début de session	127.0.0.1
8908	agarcia	2013-10-21	17:20:03.074	Consultation des entreprises	127.0.0.1
8909	agarcia	2013-10-21	17:20:09.174	Consultation des entreprises	127.0.0.1
8910	agarcia	2013-10-21	17:20:12.469	Entreprise JJO578964S02 supprimée	127.0.0.1
9128	agarcia	2013-10-24	14:49:31.852	Début de session	127.0.0.1
9129	agarcia	2013-10-24	14:50:01.913	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9130	agarcia	2013-10-24	14:50:07.067	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9131	agarcia	2013-10-24	14:50:25.791	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9132	agarcia	2013-10-24	14:51:41.309	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9133	agarcia	2013-10-24	14:51:52.559	Consultation des entreprises	127.0.0.1
9134	admin	2013-10-24	14:52:04.554	Début de session	127.0.0.1
8751	agarcia	2013-10-16	19:22:30.727	Inicio de sesión	127.0.0.1
8752	agarcia	2013-10-16	19:22:47.74	Contraseña de usuario agarcia actualizada	127.0.0.1
8753	admin	2013-10-16	19:23:39.377	Inicio de sesión	127.0.0.1
8754	admin	2013-10-16	19:23:41.186	Consulta a usuarios	127.0.0.1
8755	admin	2013-10-16	19:23:45.45	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8756	admin	2013-10-16	19:23:45.457	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8757	admin	2013-10-16	19:23:53.021	Usuario admin modificado	127.0.0.1
8758	admin	2013-10-16	19:23:54.751	Consulta a usuarios	127.0.0.1
8759	admin	2013-10-16	19:24:05.797	Inicio de sesión	127.0.0.1
8911	agarcia	2013-10-21	17:21:04.43	Début de session	127.0.0.1
8912	agarcia	2013-10-21	17:21:06.282	Consultation des entreprises	127.0.0.1
8913	agarcia	2013-10-21	17:21:13.559	Entreprise MMH987456E28 supprimée	127.0.0.1
8914	agarcia	2013-10-21	17:21:16.076	Consultation des entreprises	127.0.0.1
9135	admin	2013-10-24	14:54:31.401	Début de session	127.0.0.1
8760	agarcia	2013-10-16	19:58:49.704	Inicio de sesión	127.0.0.1
8915	agarcia	2013-10-21	17:26:16.849	Début de session	127.0.0.1
8916	admin	2013-10-21	17:26:48.061	Début de session	127.0.0.1
8917	admin	2013-10-21	17:27:02.479	Consulta a usuarios	127.0.0.1
8918	admin	2013-10-21	17:27:09.031	Acceso a tarea Permitida: addUser.form	127.0.0.1
8919	admin	2013-10-21	17:27:09.068	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8920	admin	2013-10-21	17:27:24.328	Utilisateur Michel Ajouté	127.0.0.1
8921	admin	2013-10-21	17:27:26.841	Consulta a usuarios	127.0.0.1
8922	admin	2013-10-21	17:27:29.53	Intento Accesar a una tarea no permitida: deleteUser.form	127.0.0.1
8923	admin	2013-10-21	17:27:56.983	Intento Accesar a una tarea no permitida: deleteUser.form	127.0.0.1
8924	admin	2013-10-21	17:29:16.295	Intento Accesar a una tarea no permitida: deleteUser.form	127.0.0.1
8925	admin	2013-10-21	17:29:18.726	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8926	admin	2013-10-21	17:29:18.728	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8927	admin	2013-10-21	17:29:21.722	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8928	admin	2013-10-21	17:29:21.724	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8929	admin	2013-10-21	17:29:22.495	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8930	admin	2013-10-21	17:29:22.497	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8931	admin	2013-10-21	17:29:23.823	Acceso a tarea Permitida: addUser.form	127.0.0.1
8932	admin	2013-10-21	17:29:23.825	Acceso a tarea PErmitida: addUser.form	127.0.0.1
8933	admin	2013-10-21	17:29:27.389	Consulta a usuarios	127.0.0.1
9136	admin	2013-10-24	15:03:43.738	Début de session	127.0.0.1
8761	agarcia	2013-10-16	20:59:34.105	Inicio de sesión	127.0.0.1
8762	agarcia	2013-10-16	20:59:48.231	Consulta de los documentos de la empresa MMJ745924R59	127.0.0.1
8763	agarcia	2013-10-16	20:59:55.738	Consulta de reporte de empresas	127.0.0.1
8934	admin	2013-10-21	17:30:52.296	Début de session	127.0.0.1
8935	admin	2013-10-21	17:30:54.802	Consulta a usuarios	127.0.0.1
8936	admin	2013-10-21	17:31:00.41	Intento Accesar a una tarea no permitida: deleteUser.form	127.0.0.1
8937	admin	2013-10-21	17:33:13.615	Intento Accesar a una tarea no permitida: deleteUser.form	127.0.0.1
9137	admin	2013-10-24	15:05:15.681	Début de session	127.0.0.1
8938	admin	2013-10-21	17:34:12.335	Début de session	127.0.0.1
8939	admin	2013-10-21	17:34:15.195	Consulta a usuarios	127.0.0.1
8940	admin	2013-10-21	17:34:18.086	Intento Accesar a una tarea no permitida: deleteUser.form	127.0.0.1
9138	admin	2013-10-24	15:06:26.518	Début de session	127.0.0.1
8941	admin	2013-10-21	17:35:37.281	Début de session	127.0.0.1
8942	admin	2013-10-21	17:35:39.116	Consulta a usuarios	127.0.0.1
8943	admin	2013-10-21	17:35:42.228	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8944	admin	2013-10-21	17:35:45.615	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
9139	admin	2013-10-24	15:08:40.424	Début de session	127.0.0.1
8945	admin	2013-10-21	17:37:43.561	Début de session	127.0.0.1
8946	admin	2013-10-21	17:37:48.095	Consulta a usuarios	127.0.0.1
8947	admin	2013-10-21	17:37:50.86	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8948	admin	2013-10-21	17:37:55.198	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
9140	admin	2013-10-24	15:11:27.801	Début de session	127.0.0.1
8949	admin	2013-10-21	17:39:49.177	Début de session	127.0.0.1
8950	admin	2013-10-21	17:39:51.564	Consulta a usuarios	127.0.0.1
8951	admin	2013-10-21	17:39:53.943	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
9141	admin	2013-10-24	15:14:31.913	Début de session	127.0.0.1
9142	admin	2013-10-24	15:14:40.665	Consultation des logs :C:/ITC/FC4/Logs\\2013\\10\\fc4.20131024.log du jour : 2013/10/24	127.0.0.1
8952	admin	2013-10-21	17:40:56.523	Début de session	127.0.0.1
8953	admin	2013-10-21	17:40:59.04	Consulta a usuarios	127.0.0.1
8954	admin	2013-10-21	17:41:01.91	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8955	admin	2013-10-21	17:41:04.457	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8956	admin	2013-10-21	17:41:06.441	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8957	admin	2013-10-21	17:41:59.705	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8958	admin	2013-10-21	17:41:59.731	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8959	admin	2013-10-21	17:42:03.436	Consulta a usuarios	127.0.0.1
9143	admin	2013-10-24	15:17:48.569	Début de session	127.0.0.1
9144	admin	2013-10-24	15:18:27.135	Consultation des logs :C:/ITC/FC4/Logs\\2013\\10\\fc4.20131024.log du jour : 2013/10/24	127.0.0.1
9145	admin	2013-10-24	15:18:51.723	Consultation du rapport d'activité	127.0.0.1
9146	admin	2013-10-24	15:19:05.073	Consultation des utilisateurs	127.0.0.1
8960	admin	2013-10-21	17:43:23.709	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
9147	admin	2013-10-24	15:24:25.663	Début de session	127.0.0.1
9148	admin	2013-10-24	15:24:30.037	Consultation des utilisateurs	127.0.0.1
9149	admin	2013-10-24	15:27:53.87	Consultation des utilisateurs	127.0.0.1
8961	admin	2013-10-21	17:45:08.379	Début de session	127.0.0.1
8962	admin	2013-10-21	17:45:11.73	Consulta a usuarios	127.0.0.1
8963	admin	2013-10-21	17:45:14.504	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
9150	admin	2013-10-24	15:31:52.494	Début de session	127.0.0.1
9151	admin	2013-10-24	15:32:22.983	Consultation des utilisateurs	127.0.0.1
8964	admin	2013-10-21	17:46:17.436	Début de session	127.0.0.1
8965	admin	2013-10-21	17:46:19.235	Consulta a usuarios	127.0.0.1
8966	admin	2013-10-21	17:46:24.806	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
9152	agarcia	2013-10-24	16:07:13.798	Début de session	127.0.0.1
8967	admin	2013-10-21	17:47:19.549	Début de session	127.0.0.1
8968	admin	2013-10-21	17:47:22.218	Consulta a usuarios	127.0.0.1
8969	admin	2013-10-21	17:47:24.621	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8970	admin	2013-10-21	17:47:53.77	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8971	admin	2013-10-21	17:47:53.771	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8972	admin	2013-10-21	17:49:15.994	Début de session	127.0.0.1
8973	admin	2013-10-21	17:49:18.019	Consulta a usuarios	127.0.0.1
8974	admin	2013-10-21	17:49:21.045	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8975	admin	2013-10-21	17:49:24.945	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8976	admin	2013-10-21	17:49:24.947	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8977	admin	2013-10-21	17:50:47.884	Début de session	127.0.0.1
8978	admin	2013-10-21	17:50:57.505	Consulta a usuarios	127.0.0.1
8979	admin	2013-10-21	17:51:00.228	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8980	admin	2013-10-21	17:51:02.464	Acceso a tarea Permitida: modificaUsuario.form	127.0.0.1
8981	admin	2013-10-21	17:51:02.468	Acceso a tarea PErmitida: modificaUsuario.form	127.0.0.1
8982	admin	2013-10-21	17:51:31.115	Consulta a usuarios	127.0.0.1
8983	admin	2013-10-21	17:51:32.909	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8984	admin	2013-10-21	18:05:03.209	Début de session	127.0.0.1
8985	admin	2013-10-21	18:05:21.013	Consulta a usuarios	127.0.0.1
8986	admin	2013-10-21	18:10:59.48	Intento Accesar a una tarea no permitida: eliminaUsuario.form	127.0.0.1
8987	admin	2013-10-21	18:13:03.903	Début de session	127.0.0.1
8988	admin	2013-10-21	18:13:09.892	Consulta a usuarios	127.0.0.1
8989	admin	2013-10-21	18:14:21.43	Acceso a tarea Permitida: deleteUser.form	127.0.0.1
8990	admin	2013-10-21	18:14:21.434	Acceso a tarea PErmitida: deleteUser.form	127.0.0.1
8991	agarcia	2013-10-21	18:20:19.065	Début de session	127.0.0.1
8992	agarcia	2013-10-21	18:20:21.443	Consultation des entreprises	127.0.0.1
8993	agarcia	2013-10-21	18:20:48.057	Début de session	127.0.0.1
8994	agarcia	2013-10-21	18:20:49.55	Consultation des entreprises	127.0.0.1
8995	agarcia	2013-10-21	18:20:56.83	Entreprise MME020905462 Modifiée	127.0.0.1
8996	agarcia	2013-10-21	18:20:59.449	Consultation des entreprises	127.0.0.1
8997	admin	2013-10-21	18:21:16.205	Début de session	127.0.0.1
8998	admin	2013-10-21	18:21:17.984	Consulta a usuarios	127.0.0.1
8999	admin	2013-10-21	18:21:20.653	Acceso a tarea Permitida: deleteUser.form	127.0.0.1
9000	admin	2013-10-21	18:21:20.655	Acceso a tarea PErmitida: deleteUser.form	127.0.0.1
9001	admin	2013-10-21	18:23:22.085	Début de session	127.0.0.1
9002	admin	2013-10-21	18:23:25.563	Consulta a usuarios	127.0.0.1
9003	admin	2013-10-21	18:23:28.418	Acceso a tarea Permitida: deleteUser.form	127.0.0.1
9004	admin	2013-10-21	18:23:28.421	Acceso a tarea PErmitida: deleteUser.form	127.0.0.1
9005	admin	2013-10-21	18:23:31.995	Usuario Michel eliminado	127.0.0.1
9006	admin	2013-10-21	18:23:34.733	Consulta a usuarios	127.0.0.1
9007	admin	2013-10-21	18:28:11.395	Consulta a usuarios	127.0.0.1
9008	admin	2013-10-21	18:31:30.441	Début de session	127.0.0.1
9009	admin	2013-10-21	18:31:34.262	Consulta a usuarios	127.0.0.1
9010	admin	2013-10-21	18:31:38.361	Acceso a tarea Permitida: addUser.form	127.0.0.1
9011	admin	2013-10-21	18:31:38.365	Acceso a tarea PErmitida: addUser.form	127.0.0.1
9012	admin	2013-10-21	18:31:57.641	Utilisateur Michel Ajouté	127.0.0.1
9013	admin	2013-10-21	18:32:00.438	Consulta a usuarios	127.0.0.1
9014	admin	2013-10-21	18:32:02.841	Acceso a tarea Permitida: deleteUser.form	127.0.0.1
9015	admin	2013-10-21	18:32:02.847	Acceso a tarea PErmitida: deleteUser.form	127.0.0.1
9016	admin	2013-10-21	18:32:05.396	Consulta a usuarios	127.0.0.1
9017	admin	2013-10-21	18:32:07.136	Intento Accesar a una tarea no permitida: modifyUser.form	127.0.0.1
9018	admin	2013-10-21	18:32:54.887	Début de session	127.0.0.1
9019	admin	2013-10-21	18:32:57.16	Consulta a usuarios	127.0.0.1
9020	admin	2013-10-21	18:33:00.143	Acceso a tarea Permitida: modifyUser.form	127.0.0.1
9021	admin	2013-10-21	18:33:00.146	Acceso a tarea PErmitida: modifyUser.form	127.0.0.1
9022	admin	2013-10-21	18:33:11.109	Usuario Michel modificado	127.0.0.1
9023	admin	2013-10-21	18:33:12.232	Consulta a usuarios	127.0.0.1
9024	admin	2013-10-21	18:46:20.364	Début de session	127.0.0.1
9025	admin	2013-10-21	18:46:24.685	Consulta a usuarios	127.0.0.1
9026	admin	2013-10-21	18:46:37.207	Consultation du rapport d'activité	127.0.0.1
9027	agarcia	2013-10-21	18:58:12.617	Début de session	127.0.0.1
9028	agarcia	2013-10-21	18:58:21.086	Consultation des documents de l'entreprise XEXX010101000	127.0.0.1
9029	agarcia	2013-10-21	18:58:35.582	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9030	agarcia	2013-10-21	18:58:51.678	Consultation des entreprises	127.0.0.1
9031	agarcia	2013-10-21	18:59:44.541	Consultation des entreprises	127.0.0.1
9032	agarcia	2013-10-21	19:05:14.453	Début de session	127.0.0.1
9033	agarcia	2013-10-21	19:05:28.264	Consultation des entreprises	127.0.0.1
9034	agarcia	2013-10-21	19:10:19.441	Début de session	127.0.0.1
9035	agarcia	2013-10-21	19:10:22.416	Consultation des entreprises	127.0.0.1
9036	agarcia	2013-10-21	19:10:31.203	Consultation des documents de l'entreprise MME020905462	127.0.0.1
9037	agarcia	2013-10-21	19:10:39.491	Consultation des documents de l'entreprise MME020905462	127.0.0.1
\.


--
-- TOC entry 2057 (class 0 OID 0)
-- Dependencies: 175
-- Name: activities_act_id_seq; Type: SEQUENCE SET; Schema: period; Owner: postgres
--

SELECT pg_catalog.setval('activities_act_id_seq', 9154, true);


--
-- TOC entry 2009 (class 0 OID 17479)
-- Dependencies: 176
-- Data for Name: document_events; Type: TABLE DATA; Schema: period; Owner: postgres
--

COPY document_events (emp_id, dty_id, dst_id, doc_id, evt_id, evt_date, evt_time, evt_err_code, evt_err_severity, evt_err_description, evt_err_stack) FROM stdin;
BAMJ850411K10	CFD	3.2	Ab22059c1-11ab-48fc-96df-2d3115fff1a2	1	2013-07-10	01:35:26	0	0	OK	-
TAV951205C15	CFD	3.2	A96C9D3BB-70BE-4335-A657-03819E3DF7C6	2	2013-07-10	06:00:17	0	0	OK	-
XEXX010101000	CFD	3.2	A96C9D3BB-70BE-4335-A657-03819E3DF7C6	3	2013-07-10	06:15:38	0	0	OK	-
XEXX010101000	CFD	3.2	AD3BB2BD9-6D8A-44FF-ADEA-6F79F78FBE51	4	2013-07-10	07:52:03	0	0	OK	-
XEXX010101000	CFD	3.2	A10	5	2013-07-10	11:35:27	0	0	OK	-
XEXX010101000	CFD	3.2	A101	6	2013-07-10	11:51:21	0	0	OK	-
XEXX010101000	CFD	3.2	B101	7	2013-07-10	11:54:23	0	0	OK	-
XEXX010101000	CFD	3.2	A1001	8	2013-07-11	12:01:18	0	0	OK	-
XEXX010101000	CFD	3.2	R1001	9	2013-07-11	12:26:27	0	0	OK	-
XEXX010101000	CFD	3.2	Z1001	10	2013-07-11	12:29:31	0	0	OK	-
XEXX010101000	CFD	3.2	T1001	11	2013-07-11	12:31:32	0	0	OK	-
XEXX010101000	CFD	3.2	I1001	12	2013-07-11	12:35:05	0	0	OK	-
XEXX010101000	3.2	3.2	O1001	13	2013-07-11	12:39:21	0	0	OK	-
XEXX010101000	CFDI	CFDI	K1001	14	2013-07-11	12:46:09	0	0	OK	-
XEXX010101000	CFDI	3.2	H1001	15	2013-07-11	12:49:31	0	0	OK	-
XEXX010101000	CFDI	3.2	M1001	16	2013-07-11	12:51:18	0	0	OK	-
XEXX010101000	CFDI	3.2	L1001	17	2013-07-11	01:01:05	0	0	OK	-
XEXX010101000	CFDI	3.2	W1001	18	2013-07-11	01:02:39	0	0	OK	-
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459-ABBDB2B505B2	19	2013-07-11	01:08:23	0	0	OK	-
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459	20	2013-07-11	01:29:09	0	0	OK	-
XEXX010101000	CFD	1.23	O1000	21	2013-07-11	04:38:13	0	0	OK	-
XEXX010101000	CFDI	3.2	pdoazpjfzoiefhoi	22	2013-07-11	05:36:24	0	0	OK	-
XEXX010101000	CFDI	3.2	94456b97-8496-4659-8d21-c563bf1d407e	23	2013-07-11	08:59:01	0	0	OK	-
XEXX010101000	CFDI	3.2	kii63bf1d407e	24	2013-07-11	11:35:32	0	0	OK	-
MME020905462	CFD	2.0		25	2013-08-09	12:55:38	0	0	OK	-
MME020905462	CFD	2.0	A0000003	26	2013-08-09	01:38:19	0	0	OK	-
MME020905462	CFD	2.0	A0000006	27	2013-08-09	01:43:11	0	0	OK	-
\.


--
-- TOC entry 2058 (class 0 OID 0)
-- Dependencies: 177
-- Name: document_events_evt_id_seq; Type: SEQUENCE SET; Schema: period; Owner: postgres
--

SELECT pg_catalog.setval('document_events_evt_id_seq', 27, true);


--
-- TOC entry 2011 (class 0 OID 17487)
-- Dependencies: 178
-- Data for Name: document_files; Type: TABLE DATA; Schema: period; Owner: postgres
--

COPY document_files (emp_id, dty_id, dst_id, doc_id, file_id, path, filename) FROM stdin;
BAMJ850411K10	CFD	3.2	Ab22059c1-11ab-48fc-96df-2d3115fff1a2	3	BAMJ850411K10\\2013\\07\\10\\01	A01.xml
TAV951205C15	CFD	3.2	A96C9D3BB-70BE-4335-A657-03819E3DF7C6	4	TAV951205C15\\2013\\07\\10\\06	A0010.xml
XEXX010101000	CFD	3.2	A96C9D3BB-70BE-4335-A657-03819E3DF7C6	5	XEXX010101000\\2013\\07\\10\\06	A0010.xml
XEXX010101000	CFD	3.2	AD3BB2BD9-6D8A-44FF-ADEA-6F79F78FBE51	6	XEXX010101000\\2013\\07\\10\\07	A000100.xml
XEXX010101000	CFD	3.2	A10	7	XEXX010101000\\2013\\07\\10\\11	A0010.xml
XEXX010101000	CFD	3.2	A101	8	XEXX010101000\\2013\\07\\10\\11	A000101.xml
XEXX010101000	CFD	3.2	B101	9	XEXX010101000\\2013\\07\\10\\11	B000101.xml
XEXX010101000	CFD	3.2	A1001	10	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	A1001	11	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	R1001	12	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	R1001	13	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	R1001	14	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	Z1001	15	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	Z1001	16	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	Z1001	17	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	T1001	18	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	T1001	19	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	T1001	20	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	I1001	21	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	I1001	22	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFD	3.2	I1001	23	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	3.2	3.2	O1001	24	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	3.2	3.2	O1001	25	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	3.2	3.2	O1001	26	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	CFDI	K1001	27	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	CFDI	K1001	28	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	CFDI	K1001	29	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	H1001	30	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	H1001	31	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	H1001	32	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	M1001	33	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	M1001	34	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	M1001	35	XEXX010101000\\2013\\07\\11\\12	A00001001.xml
XEXX010101000	CFDI	3.2	L1001	36	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	L1001	37	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	L1001	38	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	W1001	39	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	W1001	40	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	W1001	41	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459-ABBDB2B505B2	42	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459-ABBDB2B505B2	43	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459-ABBDB2B505B2	44	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459	45	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459	46	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459	47	XEXX010101000\\2013\\07\\11\\01	A00001001.xml
XEXX010101000	CFD	1.23	O1000	48	XEXX010101000\\2013\\07\\11\\04	A00001000.xml
XEXX010101000	CFD	1.23	O1000	49	XEXX010101000\\2013\\07\\11\\04	A00001000.xml
XEXX010101000	CFD	1.23	O1000	50	XEXX010101000\\2013\\07\\11\\04	A00001000.xml
XEXX010101000	CFD	1.23	O1000	51	XEXX010101000\\2013\\07\\11\\04	A00001000.xml
XEXX010101000	CFD	1.23	O1000	52	XEXX010101000\\2013\\07\\11\\04	A00001000.xml
XEXX010101000	CFDI	3.2	pdoazpjfzoiefhoi	53	XEXX010101000\\2013\\07\\11\\05	A00001001.xml
XEXX010101000	CFDI	3.2	94456b97-8496-4659-8d21-c563bf1d407e	54	XEXX010101000\\2013\\07\\11\\08	A02.xml
XEXX010101000	CFDI	3.2	kii63bf1d407e	55	XEXX010101000\\2013\\07\\11\\11	A02.xml
MME020905462	CFD	2.0		56	MME020905462\\2013\\08\\09\\12	A0000001.xml
MME020905462	CFD	2.0	A0000003	57	MME020905462\\2013\\08\\09\\01	A0000003.xml
MME020905462	CFD	2.0	A0000006	58	MME020905462\\2013\\08\\09\\01	A0000006.xml
\.


--
-- TOC entry 2059 (class 0 OID 0)
-- Dependencies: 179
-- Name: document_files_file_id_seq; Type: SEQUENCE SET; Schema: period; Owner: postgres
--

SELECT pg_catalog.setval('document_files_file_id_seq', 58, true);


--
-- TOC entry 2013 (class 0 OID 17495)
-- Dependencies: 180
-- Data for Name: documents; Type: TABLE DATA; Schema: period; Owner: postgres
--

COPY documents (emp_id, dty_id, dst_id, doc_id, doc_creation_date, doc_creation_time, doc_last_mod_date, doc_last_mod_time, emp_id_from, doc_status) FROM stdin;
BAMJ850411K10	CFD	3.2	Ab22059c1-11ab-48fc-96df-2d3115fff1a2	2012-09-03	11:52:13-05	2013-07-10	01:35:26-05	XAXX010101000	O
TAV951205C15	CFD	3.2	A96C9D3BB-70BE-4335-A657-03819E3DF7C6	2012-08-23	10:22:28-05	2013-07-10	06:00:17-05	TAV951205C15	O
XEXX010101000	CFD	3.2	A96C9D3BB-70BE-4335-A657-03819E3DF7C6	2012-08-23	10:22:28-05	2013-07-10	06:15:38-05	TAV951205C15	O
XEXX010101000	CFD	3.2	AD3BB2BD9-6D8A-44FF-ADEA-6F79F78FBE51	2012-09-10	06:37:06-05	2013-07-10	07:52:03-05	IAL830622P68	O
XEXX010101000	CFD	3.2	A10	2012-08-23	10:22:28-05	2013-07-10	11:35:27-05	XEXX010101000	O
XEXX010101000	CFD	3.2	A101	2012-08-09	08:38:29-05	2013-07-10	11:51:21-05	LEI870520PG8	O
XEXX010101000	CFD	3.2	B101	2012-08-13	07:56:31-05	2013-07-10	11:54:23-05	MAB911203RR7	O
XEXX010101000	CFD	3.2	A1001	2012-08-22	05:19:42-05	2013-07-11	12:01:18-05	PIN860514S81	O
XEXX010101000	CFD	3.2	R1001	2012-08-22	05:19:42-05	2013-07-11	12:26:27-05	PIN860514S81	O
XEXX010101000	CFD	3.2	Z1001	2012-08-22	05:19:42-05	2013-07-11	12:29:31-05	PIN860514S81	O
XEXX010101000	CFD	3.2	T1001	2012-08-22	05:19:42-05	2013-07-11	12:31:32-05	PIN860514S81	O
XEXX010101000	CFD	3.2	I1001	2012-08-22	05:19:42-05	2013-07-11	12:35:05-05	PIN860514S81	O
XEXX010101000	3.2	3.2	O1001	2012-08-22	05:19:42-05	2013-07-11	12:39:21-05	PIN860514S81	O
XEXX010101000	CFDI	CFDI	K1001	2012-08-22	05:19:42-05	2013-07-11	12:46:09-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	H1001	2012-08-22	05:19:42-05	2013-07-11	12:49:31-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	M1001	2012-08-22	05:19:42-05	2013-07-11	12:51:18-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	L1001	2012-08-22	05:19:42-05	2013-07-11	01:01:05-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	W1001	2012-08-22	05:19:42-05	2013-07-11	01:02:39-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459-ABBDB2B505B2	2012-08-22	05:19:42-05	2013-07-11	01:08:23-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459	2012-08-22	05:19:42-05	2013-07-11	01:29:09-05	PIN860514S81	O
XEXX010101000	CFD	1.23	O1000	2012-08-22	05:16:15-05	2013-07-11	04:38:13-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	pdoazpjfzoiefhoi	2012-08-22	05:19:42-05	2013-07-11	05:36:24-05	PIN860514S81	O
XEXX010101000	CFDI	3.2	94456b97-8496-4659-8d21-c563bf1d407e	2012-09-03	11:51:57-05	2013-07-11	08:59:01-05	XAXX010101000	O
XEXX010101000	CFDI	3.2	kii63bf1d407e	2012-09-03	11:51:57-05	2013-07-11	11:35:32-05	XAXX010101000	O
MME020905462	CFD	2.0		2012-12-21	02:47:34-05	2013-08-09	12:55:38-05	VIM0108105N3	O
MME020905462	CFD	2.0	A0000003	2012-01-07	02:30:37-05	2013-08-09	01:38:19-05	VIM0108105N3	O
MME020905462	CFD	2.0	A0000006	2012-01-11	02:31:37-05	2013-08-09	01:43:11-05	VIM0108105N3	O
\.


--
-- TOC entry 2014 (class 0 OID 17498)
-- Dependencies: 181
-- Data for Name: dtype_cfd; Type: TABLE DATA; Schema: period; Owner: postgres
--

COPY dtype_cfd (emp_id, dty_id, dst_id, doc_id, cfd_id2, cfd_folio, cfd_type, cfd_currency, cfd_total, cfd_status, cfd_serie) FROM stdin;
XEXX010101000	CFDI	3.2	94456b97-8496-4659-8d21-c563bf1d407e	A2	15	CFDI	MXN	1.1599999999999999	O	A
XEXX010101000	CFDI	3.2	kii63bf1d407e	A20	7	CFDI	MXN	1.1599999999999999	O	B
XEXX010101000	CFDI	3.2	7A935C01-A476-4C55-8459	\N	300	CFDI	ZZZ	1.1000000000000001	o	Z
MME020905462	CFD	2.0		A0000001	1	CFD	   	43139.120000000003	O	A
MME020905462	CFD	2.0	A0000003	A0000003	3	CFD	   	92719.559999999998	O	A
MME020905462	CFD	2.0	A0000006	A0000006	6	CFD	   	248139.09	O	A
\.


--
-- TOC entry 2015 (class 0 OID 17501)
-- Dependencies: 182
-- Data for Name: errors; Type: TABLE DATA; Schema: period; Owner: postgres
--

COPY errors (err_num, err_date, err_time, err_path, err_filename, err_code, err_description, err_stack) FROM stdin;
\.


--
-- TOC entry 2060 (class 0 OID 0)
-- Dependencies: 183
-- Name: errors_err_num_seq; Type: SEQUENCE SET; Schema: period; Owner: postgres
--

SELECT pg_catalog.setval('errors_err_num_seq', 1, false);


-- Completed on 2013-10-26 12:58:55

--
-- PostgreSQL database dump complete
--

