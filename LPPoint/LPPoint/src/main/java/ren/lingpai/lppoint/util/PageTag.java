package ren.lingpai.lppoint.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by lrp on 17-5-21.
 */
public class PageTag extends TagSupport {

    private PageParam value;
    private String url;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        if(value == null) {
            return super.doStartTag();
        }
        try {
            //算出第几条数据
            //开始 的条数
            int startInfo=value.getCurrentPage()*value.getPageSize()-(value.getPageSize()-1);
            int endInfo=value.getCurrentPage()*value.getPageSize();

            if(value.getTotalCount()==0){
                startInfo=0;
                endInfo=value.getTotalCount();
            }
            else{
                endInfo=endInfo>value.getTotalCount()?value.getTotalCount():endInfo;
            }

            //获得总页数
            int totalPage=value.getTotalPage();

            out.println("<div class='media text-center'>");
            out.println("<ul class='pagination'>");
            if(value.getTotalPage()>1){
                boolean bool = url.indexOf("?")>0;//判断后面字符串用？还是&
                String sepe ="";
                if(bool){
                    sepe = "&";
                }else {
                    sepe = "?";
                }
                if (value.getCurrentPage()>1) {
                    out.println("<li><a href='" + url+sepe+"currentPage="+(value.getCurrentPage()-1) + "'><i class='fa fa-angle-left'></i></a></li>");
                }
                else{
                    out.println("<li><a href='#'><i class='fa fa-angle-left'></i></a></li>");
                }

                if (value.getCurrentPage()==2) {
                    out.println("<li><a href='" + url+sepe+"currentPage=1" + "'>1</a></li>");
                }
                if (value.getCurrentPage()>2) {
                    out.println("<li><a href='" + url+sepe+"currentPage=1" + "'>1</a></li>");
                    out.println("<li><a href='#'>...</a></li>");
                }

                out.println("<li><a style='background: rgba(255,255,255,0.1);' href='" + url+sepe+"currentPage=" + value.getCurrentPage() + "'>" + value.getCurrentPage() +"</a></li>");

                if (value.getTotalPage()-value.getCurrentPage()>1) {
                    out.println("<li><a href='#'>...</a></li>");
                    out.println("<li><a href='" + url+sepe+"currentPage=" + value.getTotalPage() + "'>" + value.getTotalPage() + "</a></li>");
                }
                if (value.getTotalPage()-value.getCurrentPage()==1) {
                    out.println("<li><a href='" + url+sepe+"currentPage=" + value.getTotalPage() + "'>" + value.getTotalPage() + "</a></li>");
                }
                if(value.getCurrentPage()-value.getTotalPage()<0){
                    out.println("<li><a href='" + url+sepe+"currentPage="+(value.getCurrentPage()+1) + "'><i class='fa fa-angle-right'></i></a></li>");
                }
                else{
                    out.println("<li><a href='#'><i class='fa fa-angle-right'></i></a></li>");
                }
            }
            out.println("</ul>");
            out.println("</div>");

            /*
            out.println("<div class='pagination ue-clear' style='margin-top:0px;margin-bottom:50px;'>");
            out.println("<div class='pxofy'>显示第&nbsp;"+startInfo+"&nbsp;条到&nbsp;"+endInfo+"&nbsp;条记录，总共&nbsp;"+value.getTotalCount()+"&nbsp;条</div>");
            out.println("<div class='goto'><span class='text'>转到第</span><input id='inputPage' url='"+url+"' totalPage='"+totalPage+"' type='text'/><span class='page'>页</span><a href='javascript:void(0)' onclick='getInfoByPage()'>转</a></div>");
            if(value.getTotalPage()>1){
                out.println("<div class='pagin-list'>");
                boolean bool = url.indexOf("?")>0;//判断后面字符串用？还是&
                String sepe ="";
                if(bool){
                    sepe = "&";
                }else {
                    sepe = "?";
                }
                if (value.getCurrentPage()>1) {
                    out.println("<a href="+url+sepe+"currentPage="+(value.getCurrentPage()-1)+ " class='prev' style='text-decoration:none;'>&lt;&nbsp;上一页</a>");
                }
                else{
                    out.println("<span class='prev'>&lt;&nbsp;上一页</span>");
                }

                if (value.getCurrentPage()>=7) {
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage=1"+" >1</a>");
                    out.println("<i>...</i>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()-4) +">"+(value.getCurrentPage()-4)+"</a>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()-3) +">"+(value.getCurrentPage()-3)+"</a>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()-2) +">"+(value.getCurrentPage()-2)+"</a>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()-1) +">"+(value.getCurrentPage()-1)+"</a>");
                }else{
                    for (int i = 1; i <= value.getCurrentPage()-1; i++) {
                        out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(i) +">"+(i)+"</a>");
                    }
                }

                out.println("<span class='current'>"+value.getCurrentPage()+"</span>");

                if (value.getTotalPage()-value.getCurrentPage()>=6) {
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()+1) +">"+(value.getCurrentPage()+1)+"</a>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()+2) +">"+(value.getCurrentPage()+2)+"</a>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()+3) +">"+(value.getCurrentPage()+3)+"</a>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()+4) +">"+(value.getCurrentPage()+4)+"</a>");
                    out.println("<span>...</span>");
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+value.getTotalPage()+" >"+value.getTotalPage()+"</a>");
                }else{
                    for (int i = value.getCurrentPage()+1; i <= value.getTotalPage(); i++) {
                        out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(i) +">"+(i)+"</a>");
                    }
                }
                if(value.getCurrentPage()-value.getTotalPage()<0){
                    out.println("<a style='text-decoration:none;' href="+url+sepe+"currentPage="+(value.getCurrentPage()+1)+" class='next' >下一页&nbsp;&gt;</a>");
                }
                else{
                    out.println("<span class='prev'>下一页&nbsp;&gt;</span>");
                }
            }
            out.println("</div></div>");
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.doStartTag();
        return SKIP_BODY;
    }

    @Override
    public void release() {
        super.release();
        this.value = null;
    }

    public PageParam getValue() {
        return value;
    }

    public void setValue(PageParam value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
