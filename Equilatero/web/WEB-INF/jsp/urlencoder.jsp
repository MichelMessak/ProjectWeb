<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="com.itc.fc4.util.Util"%>
<%

            try
            {
                String fileName = request.getParameter("archive");
                if(fileName==null) fileName=(String)request.getAttribute("archive");
                String repositoryFile = request.getParameter("repository");
                if(repositoryFile==null) repositoryFile=(String)request.getAttribute("repository");
                String completeFile=null;
                if(repositoryFile!=null)
                    completeFile=repositoryFile+"\\"+ fileName;
                else
                    completeFile=fileName;

                if(completeFile==null)
                    throw new Exception("Aucun Fichier");
                FileInputStream file = new FileInputStream(completeFile);
                int longitud = file.available();
                byte[] data = new byte[longitud];
                file.read(data);
                file.close();
                response.setContentType(Util.getContentTypeForExtension(fileName));
                if("true".equals(request.getParameter("download")))
                    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                else
                    response.setHeader("Content-Disposition", "inline;filename=" + fileName);
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(data);
                ouputStream.flush();
                ouputStream.close();
            }
            catch (Exception ex)
            {
                request.setAttribute("exception", ex);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
%>