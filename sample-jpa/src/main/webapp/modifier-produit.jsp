<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ page 
    
    import="java.util.ArrayList,java.util.List,com.nextu.entities.Categorie,com.nextu.entities.Produit"
 %>
 <%
  List<Categorie> categories = new ArrayList<Categorie>();
  if(request.getAttribute("categories")!=null){
	  categories = (ArrayList<Categorie>)request.getAttribute("categories");
  }
  Produit produit = (Produit)request.getAttribute("produit");
%>
<div class="container-fluid">
    <div class="row">
     <%@ include file="/WEB-INF/template/menu.jsp" %>
      <main class="col-md-10 ml-sm-auto col-lg-10 pt-3 px-4">
        <%@ include file="/WEB-INF/error/error-message.jsp" %>
        <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Modifier un produit <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <form method="post" action="modifierProduit">
                  <div class="form-group">
                    <input type="text" class="form-control" placeholder="Intitul� de la cat�gorie" name="libelle" value="<%= produit.getName()%>">
                  </div>
                  <div class="form-group">
                    <select name="idCategorie" id="categorie">
                       <option value="-1">Choix de la cat�gorie</option>
                       	<%
                    		 for(Categorie sp :categories){
                  	    %>
                       	<option 
                       		value="<%=sp.getId() %>" 
                       		<%if(sp.getId().equals(produit.getCategorie().getId())){ %>
                       		selected=selected
                       		<%} %>
                       		><%=sp.getName() %>
                        </option>
                        <%} %>		
                    </select>
                  </div>
                  <div class="form-group row">
                    <div class="col-sm-10">
                      <button type="submit" class="btn btn-primary">
                        <i class="fa fa-send"></i>
                        Enregistrer
                      </button>
                      <button type="submit" class="btn btn-primary">
                        <i class="fa fa-send"></i>
                        Annuler
                      </button>
                    </div>
                    </div>
                     <div class="form-group row">
                    <div class="col-sm-10">
                       <input type="hidden" class="form-control" name="idProduit" value="<%= produit.getId()%>">
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</body>
</html>