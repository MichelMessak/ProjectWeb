package fr.esiea.fc.model.document;

import java.net.URLEncoder;

/**
 * @author Dispa Cécile
 */

public class DocFile {
    private String enterpriseId;
    private String docTypeId;
    private String docSubtypeId;
    private String docId;
    private Integer fileId;
    private String path;
    private String filename;

    public DocFile(String enterpriseId, String docTypeId, String docSubtypeId, String docId, Integer fileId, String path, String filename) {
        this.enterpriseId = enterpriseId;
        this.docTypeId = docTypeId;
        this.docSubtypeId = docSubtypeId;
        this.docId = docId;
        this.fileId = fileId;
        this.path = path;
        this.filename = filename;
    }

    public DocFile() {
    }

    public String getDocSubtypeId() {
        return this.docSubtypeId;
    }

    public void setDocSubtypeId(String docSubtypeId) {
        this.docSubtypeId = docSubtypeId;
    }

    public String getDocTypeId() {
        return this.docTypeId;
    }

    public void setDocTypeId(String docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getEnterpriseID() {
        return this.enterpriseId;
    }

    public void setEnterpriseID(String empId) {
        this.enterpriseId = empId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilenameURLEncoded()
    {
        return URLEncoder.encode(filename);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public String getPathURLEncoded()
    {
        return URLEncoder.encode(path);
    }
    
    public void setPath(String path) {
        this.path = path;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    


}
