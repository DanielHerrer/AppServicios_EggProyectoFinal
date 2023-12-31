package com.egg.servicios.infra;

/**
 *
 * @Martin
 */
public class MensajeHTML {

    public static final String TEMPLATE_PRUEBA = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "\n"
            + "<head>\n"
            + "	<meta charset=\"UTF-8\" />\n"
            + "	<style>\n"
            + "		.bg {\n"
            + "			width: 100%;\n"
            + "			background: white;\n"
            + "		}\n"
            + "\n"
            + "		.cont {\n"
            + "			width: 600px;\n"
            + "			margin: 0 auto;\n"
            + "			background: white;\n"
            + "		}\n"
            + "\n"
            + "		.boxCont {\n"
            + "			width: 70%;\n"
            + "			margin: 0 auto;\n"
            + "		}\n"
            + "\n"
            + "		.boxCont p {\n"
            + "			font-size: 18px;\n"
            + "			color: #231f20;\n"
            + "		}\n"
            + "\n"
            + "		.boxCode {\n"
            + "			text-align: center;\n"
            + "			border-radius: 10px;\n"
            + "			background-color: #7788e7;\n"
            + "			font-size: 16px;\n"
            + "			padding: auto;\n"
            + "		}\n"
            + "\n"
            + "		.boxCode p {\n"
            + "			width: 12%;\n"
            + "			height: 40px;\n"
            + "			font-size: 16px;\n"
            + "			display: inline-block;\n"
            + "			border-bottom: 1px #fff solid;\n"
            + "			border-radius: 5px;\n"
            + "			text-align: center;\n"
            + "			font-size: 32px;\n"
            + "			margin-left: 5px;\n"
            + "			color: #fff;\n"
            + "		}\n"
            + "\n"
            + "		.boxCont .footer {\n"
            + "			font-size: 12px;\n"
            + "			color: #949595;\n"
            + "			text-align: center;\n"
            + "		}\n"
            + "\n"
            + "		.cont .fo {\n"
            + "			width: 100%;\n"
            + "			height: 10px;\n"
            + "			margin: 20px 0 0 0;\n"
            + "			background: linear-gradient(90deg, #4155c4, #1b04a1);\n"
            + "		}\n"
            + "	</style>\n"
            + "</head>\n"
            + "\n"
            + "<body>\n"
            + "	<div class=\"bg\">\n"
            + "		<div class=\"cont\">\n"
            + "			<div class=\"boxCont\">\n"
            + "				<p><strong>Estimado usuario</strong>,</p>\n"
            + "				<p>Su código de verificación para el inicio de sesión es</p>\n"
            + "				<div class=\"boxCode\">\n"
            + "					<p>{0}</p>\n"
            + "					<p>{1}</p>\n"
            + "					<p>{2}</p>\n"
            + "					<p>{3}</p>\n"
            + "					<p>{4}</p>\n"
            + "					<p>{5}</p>\n"
            + "				</div>\n"
            + "				<p>Este código es de un solo uso</p>\n"
            + "				<br>\n"
            + "				<p class=\"footer\">Este mensaje de correo electrónico se ha enviado desde una\n"
            + "					dirección exclusivamente para envíos. No responda este mensaje.</p>\n"
            + "			</div>\n"
            + "		</div>\n"
            + "	</div>\n"
            + "</body>\n"
            + "</head>\n"
            + "\n"
            + "</html>";

}
