<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ page 
    import="java.util.ArrayList,java.util.List,com.nextu.entities.Produit"
 %>
 <%
  List<Produit> produits = new ArrayList<Produit>();
  if(request.getAttribute("produits")!=null){
	  produits = (ArrayList<Produit>)request.getAttribute("produits");
	  
  }
%>
<div class="container-fluid">
    <div class="row">
      <%@ include file="/WEB-INF/template/menu.jsp" %>
      <main class="col-md-10 ml-sm-auto col-lg-10 pt-3 px-4">
         <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
          <div class="btn-toolbar mb-2 mb-md-0">
            <div class="btn-group mr-2">
		   		<a href="creerProduit" class="btn btn-sm btn-primary">Enregistrer</a>        
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Table <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead class="thead bg-primary text-white">
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Id</th>
                      <th scope="col">Nom</th>
                      <th scope="col">Categorie</th>
                      <th scope="col">Prix</th>
                      <th scope="col">Description</th>
                      <th scope="col">Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                 	<%
                     for(Produit dsp :produits){
                  %>
                    <tr>
                  	  <td><%= dsp.getId()%></td>
                      <td><%= dsp.getName()%></td>
                      <td><%= dsp.getCategorie().getName()%></td>
                      <td><%= dsp.getPrice()%></td>
                      <td><%= dsp.getDescription()%></td>
                      <td>
                          <a class="btn btn-sm btn-success" href="modifierProduit?idProduit=<%= dsp.getId()%>">Modifier</a>
                            <!-- Button trigger modal -->
                          <a href="deleteCategorie?idCategorie=<%= dsp.getId()%>" class="btn btn-sm btn-danger">
                            supprimer
                          </a>
                      </td>
                    </tr>
                  <%
                    }
                  %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</body>
</html>