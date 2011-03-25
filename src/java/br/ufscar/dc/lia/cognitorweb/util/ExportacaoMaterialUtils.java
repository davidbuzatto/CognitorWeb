/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.util;

import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.LayoutMaterial;
import br.ufscar.dc.lia.cognitorweb.scorm.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import org.apache.commons.io.*;
import org.apache.commons.io.output.*;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Classe para a exportação do material.
 *
 * @author David Buzatto
 */
public class ExportacaoMaterialUtils {

    // caminho do material
    private String path;

    // onde vai se dar o build
    private String buildPath;

    // onde o arquivo final do pacote vai ser movido
    private String distPath;

    // nome do arquivo a ser gerado (sem extensão)
    private String nomeArquivo;

    // onde os dados serão copiados (dentro do build)
    private String dataPath;

    // onde os assets que serão copiados se encontram
    private String sourceAssetsPath;

    // caminhos dos componentes
    private String assetsPath;
    private String cssPath;
    private String jsPath;
    private String scoPath;

    // caminho das xsds
    private String commonPath;
    private String extendPath;
    private String uniquePath;
    private String vocabPath;

    // caminho dos arquivos de template
    private String templatePath;

    // material que vai ser exportado
    private Material material;

    // tipo do pacote
    private String tipo;

    private ResourceBundle rs;
    private HibernateTemplate ht;

    public ExportacaoMaterialUtils( String path,
            String nomeArquivo, String templatePath, String tipo, 
            Material material, ResourceBundle rs,
            HibernateTemplate ht ) {

        this.path = path;

        this.buildPath = path + "/build";
        this.distPath = path + "/dist";
        this.sourceAssetsPath = path + "/arquivos";

        this.nomeArquivo = nomeArquivo;
        this.templatePath = templatePath;
        this.material = material;
        this.tipo = tipo;

        this.rs = rs;
        this.ht = ht;

        dataPath = buildPath + "/" + nomeArquivo;
        assetsPath = dataPath + "/assets";
        cssPath = dataPath + "/css";
        jsPath = dataPath + "/js";
        scoPath = dataPath + "/sco";

        commonPath = dataPath + "/common";
        extendPath = dataPath + "/extend";
        uniquePath = dataPath + "/unique";
        vocabPath = dataPath + "/vocab";
        
    }

    /*
     * Cria o pacote.
     */
    public void exportarPacote() throws Exception {
        limpar();
        gerarEstrutura();
        gerarArquivos();
        copiarArquivosBase();
        gerarManifesto();
        gerarPacote();
    }

    /*
     * Limpa diretórios de exportação.
     */
    private void limpar() throws Exception {
        Uteis.deleteDirectoryContent( new File( buildPath ) );
        Uteis.deleteDirectoryContent( new File( distPath ) );
    }

    /*
     * Gera a estrutura do build.
     */
    private void gerarEstrutura() throws Exception {

        File templateDir = new File( templatePath );
        File fAssets = new File( assetsPath );
        File fCss = new File( cssPath );
        File fJs = new File( jsPath );
        File fSco = new File( scoPath );

        fAssets.mkdir();
        fCss.mkdir();
        fJs.mkdir();
        fSco.mkdir();

        FileUtils.copyDirectory( new File( templatePath + "/assets" ), fAssets );
        FileUtils.copyDirectory( new File( templatePath + "/css" ), fCss );
        FileUtils.copyDirectory( new File( templatePath + "/js" ), fJs );
        FileUtils.copyDirectory( new File( templatePath + "/sco" ), fSco );

        if ( tipo.equals( "scorm" ) ) {
            File fComm = new File( commonPath );
            File fExt = new File( extendPath );
            File fUni = new File( uniquePath );
            File fVoc = new File( vocabPath );

            fComm.mkdir();
            fExt.mkdir();
            fUni.mkdir();
            fVoc.mkdir();

            FileUtils.copyDirectory( new File( templatePath + "/common" ), fComm );
            FileUtils.copyDirectory( new File( templatePath + "/extend" ), fExt );
            FileUtils.copyDirectory( new File( templatePath + "/unique" ), fUni );
            FileUtils.copyDirectory( new File( templatePath + "/vocab" ), fVoc );
        }

    }

    /*
     * Gera todos os arquivos do material.
     */
    private void gerarArquivos() throws Exception {

        // copiar assets
        copiarAssets();

        // gera as páginas do material
        gerarPaginas( material );

        // gera os dados da estrutura do material exportado
        String dados = gerarDados( material );

        FileWriterWithEncoding fw = new FileWriterWithEncoding( new File( jsPath + "/dados.js" ), Charset.forName( "UTF-8" ) );
        fw.write( dados );
        fw.close();

    }

    /*
     * Copia os arquivos base (xml e xsd para SCORM, index.html para html)
     */
    private void copiarArquivosBase() throws Exception {

        if ( tipo.equals( "scorm" ) ) {
            File d = new File( templatePath );
            for ( File f : d.listFiles() ) {
                if ( f.getName().endsWith( ".xml" ) || 
                        f.getName().endsWith( ".xsd" ) ||
                        f.getName().endsWith( ".dtd" ) )
                    FileUtils.copyFileToDirectory( f, new File( dataPath ) );
            }
        } else if ( tipo.equals( "html" ) ) {
            FileUtils.copyFileToDirectory( new File( templatePath + "/index.html" ), new File( dataPath ) );
        }

    }


    /*
     * Gera o conteúdo do arquivo de manifesto (imsmanifest.xml)
     */
    private void gerarManifesto() throws Exception {

        if ( tipo.equals( "scorm" ) ) {

            Manifest m = new Manifest();
            m.setIdentifier( "material-" + material.getId() );
            m.setVersion( "1.0" );
            m.setBase( "./" );
            m.setMetadata( material.getMetadata() );

            Organizations os = new Organizations();
            List<Organization> orgs = new ArrayList<Organization>();
            os.setDefaultValue( "material-o-" + material.getId() );
            os.setOrganizations( orgs );
            m.setOrganizations( os );

            Resources res = new Resources();
            List<Resource> lres = new ArrayList<Resource>();
            res.setResources( lres );
            m.setResources( res );


            // criando as organizações - os recursos de página são feitos aqui...
            Organization o = new Organization();
            List<Item> itens = new ArrayList<Item>();

            o.setIdentifier( "material-o-" + material.getId() );
            o.setTitle( material.getTitulo() );
            o.setMetadata( material.getMetadata() );
            o.setItems( itens );
            orgs.add( o );

            for ( MaterialComConceito mc : material.getMateriaisComConceitos() ) {
                popularManifesto( mc.getConceito(), itens, lres );
            }

            for ( MaterialComGrupo mg : material.getMateriaisComGrupos() ) {
                popularManifesto( mg.getGrupo(), itens, lres );
            }


            // criando os recursos que estão faltando (arquivos)
            for ( File f : new File( assetsPath + "/imagens" ).listFiles() ) {
                if ( !f.isDirectory() ) {
                    Resource r = new Resource();
                    r.setIdentifier( "imagem-r-" + f.getName() );
                    r.setHref( "assets/imagens/" + f.getName() );
                    r.setBase( "./" );
                    r.setScormType( Resource.ScormType.ASSET );

                    ResourceFile fi = new ResourceFile();
                    fi.setHref( r.getHref() );
                    List<ResourceFile> lfis = new ArrayList<ResourceFile>();
                    lfis.add( fi );

                    r.setFiles( lfis );

                    lres.add( r );
                }
            }

            for ( File f : new File( assetsPath + "/videos" ).listFiles() ) {
                if ( !f.isDirectory() ) {
                    Resource r = new Resource();
                    r.setIdentifier( "video-r-" + f.getName() );
                    r.setHref( "assets/videos/" + f.getName() );
                    r.setBase( "./" );
                    r.setScormType( Resource.ScormType.ASSET );

                    ResourceFile fi = new ResourceFile();
                    fi.setHref( r.getHref() );
                    List<ResourceFile> lfis = new ArrayList<ResourceFile>();
                    lfis.add( fi );

                    r.setFiles( lfis );

                    lres.add( r );
                }
            }

            for ( File f : new File( assetsPath + "/sons" ).listFiles() ) {
                if ( !f.isDirectory() ) {
                    Resource r = new Resource();
                    r.setIdentifier( "som-r-" + f.getName() );
                    r.setHref( "assets/sons/" + f.getName() );
                    r.setBase( "./" );
                    r.setScormType( Resource.ScormType.ASSET );

                    ResourceFile fi = new ResourceFile();
                    fi.setHref( r.getHref() );
                    List<ResourceFile> lfis = new ArrayList<ResourceFile>();
                    lfis.add( fi );

                    r.setFiles( lfis );

                    lres.add( r );
                }
            }

            FileWriterWithEncoding fw = new FileWriterWithEncoding( new File( dataPath + "/imsmanifest.xml" ), Charset.forName( "UTF-8" ), true );
            Serializer s = new Persister();
            s.write( m, fw );
            fw.close();

        }

    }


    /*
     * Popula o manifesto.
     */
    private void popularManifesto( ObjetoAprendizagem oa,
            List<Item> itens, List<Resource> res ) {

        if ( oa instanceof Conceito ) {

            Conceito c = ( Conceito ) oa;
            Item i = new Item();
            List<Item> lItens = new ArrayList<Item>();

            i.setIdentifier( "conceito-i-" + c.getId() );
            i.setTitle( c.getTitulo() );
            i.setMetadata( c.getMetadata() );

            i.setItems( lItens );

            itens.add( i );

            for ( ConceitoComPagina cp : c.getConceitosComPaginas() ) {
                criarItemManifesto( cp.getPagina(), lItens, res );
            }

            for ( ConceitoComConceito cc : c.getConceitos() ) {
                popularManifesto( cc.getConceitoQueContem(), lItens, res );
            }

        }

        if ( oa instanceof Grupo ) {

            Grupo g = ( Grupo ) oa;
            Item i = new Item();
            List<Item> lItens = new ArrayList<Item>();

            i.setIdentifier( "grupo-i-" + g.getId() );
            i.setTitle( g.getTitulo() );
            i.setMetadata( g.getMetadata() );

            i.setItems( lItens );

            itens.add( i );

            // ordenando...
            TreeSet<ObjetoAprendizagemOrdenado> oaos = new TreeSet<ObjetoAprendizagemOrdenado>();

            for ( GrupoComPagina gp : g.getGruposComPaginas() ) {
                oaos.add( new ObjetoAprendizagemOrdenado( gp.getPagina(), gp.getOrdem() ) );
            }

            for ( GrupoComGrupo gg : g.getGrupos() ) {
                oaos.add( new ObjetoAprendizagemOrdenado( gg.getGrupoQueContem(), gg.getOrdem() ) );
            }

            for ( ObjetoAprendizagemOrdenado oao : oaos ) {
                if ( oao.oa instanceof Pagina )
                    criarItemManifesto( ( Pagina ) oao.oa, lItens, res );
                else if ( oao.oa instanceof Grupo )
                    popularManifesto( oao.oa, lItens, res );
            }

        }

    }


    /*
     * Cria uma página dentro do manifesto, gerando um item e um recurso.
     */
    private void criarItemManifesto( Pagina pagina, List<Item> itens, List<Resource> res ) {

        Item item = new Item();
        item.setIdentifier( "pagina-" + pagina.getId() );
        item.setIdentifierref( "sco_" + pagina.getId() );
        item.setTitle( pagina.getTitulo() );
        item.setMetadata( pagina.getMetadata() );
        itens.add( item );

        Resource r = new Resource();
        r.setIdentifier( "sco_" + pagina.getId() );
        r.setHref( "sco/sco_" + pagina.getId() + ".html" );
        r.setBase( "./" );
        r.setScormType( Resource.ScormType.SCO );

        List<ResourceFile> lfis = new ArrayList<ResourceFile>();
        r.setFiles( lfis );

        ResourceFile fi = new ResourceFile();
        fi.setHref( "sco/sco_" + pagina.getId() + ".html" );
        lfis.add( fi );

        for ( Imagem i : pagina.getImagens() ) {
            fi = new ResourceFile();
            fi.setHref( "assets/imagens/" + i.getNomeArquivo() );
            lfis.add( fi );
        }

        for ( Video v : pagina.getVideos() ) {
            fi = new ResourceFile();
            fi.setHref( "assets/videos/" + v.getNomeArquivo() );
            lfis.add( fi );
        }

        for ( Som s : pagina.getSons() ) {
            fi = new ResourceFile();
            fi.setHref( "assets/sons/" + s.getNomeArquivo() );
            lfis.add( fi );
        }

        res.add( r );

    }


    /*
     * Gera o pacote, zipando
     */
    private void gerarPacote() throws Exception {

        File arquivo = new File( dataPath + ".zip" );

        new Zipper().criarZip( arquivo, new File( dataPath ).listFiles() );

        // move o arquivo para a pasta dist
        FileUtils.moveFileToDirectory( arquivo, new File( distPath ), false );

    }


    /*
     * Faz a cópia dos assets.
     */
    private void copiarAssets() throws Exception {

        FileUtils.copyDirectoryToDirectory( new File( sourceAssetsPath + "/imagens" ), new File( assetsPath ) );
        FileUtils.copyDirectoryToDirectory( new File( sourceAssetsPath + "/videos" ), new File( assetsPath ) );
        FileUtils.copyDirectoryToDirectory( new File( sourceAssetsPath + "/sons" ), new File( assetsPath ) );

    }

    /*
     * Gerador de páginas.
     */
    private void gerarPaginas( ObjetoAprendizagem oa ) throws Exception {

        if ( oa instanceof Material ) {

            Material m = ( Material ) oa;

            for ( MaterialComConceito mc : m.getMateriaisComConceitos() ) {
                gerarPaginas( mc.getConceito() );
            }

            for ( MaterialComGrupo mg : m.getMateriaisComGrupos() ) {
                gerarPaginas( mg.getGrupo() );
            }

        }

        if ( oa instanceof Conceito ) {

            Conceito c = ( Conceito ) oa;

            for ( ConceitoComPagina cp : c.getConceitosComPaginas() ) {
                gerarPaginas( cp.getPagina() );
            }

            for ( ConceitoComConceito cc : c.getConceitos() ) {
                gerarPaginas( cc.getConceitoQueContem() );
            }

        }

        if ( oa instanceof Grupo ) {

            Grupo g = ( Grupo ) oa;

            // ordenando...
            TreeSet<ObjetoAprendizagemOrdenado> oaos = new TreeSet<ObjetoAprendizagemOrdenado>();

            for ( GrupoComPagina gp : g.getGruposComPaginas() ) {
                oaos.add( new ObjetoAprendizagemOrdenado( gp.getPagina(), gp.getOrdem() ) );
            }

            for ( GrupoComGrupo gg : g.getGrupos() ) {
                oaos.add( new ObjetoAprendizagemOrdenado( gg.getGrupoQueContem(), gg.getOrdem() ) );
            }

            for ( ObjetoAprendizagemOrdenado oao : oaos ) {
                gerarPaginas( oao.oa );
            }

        }

        if ( oa instanceof Pagina ) {

            Pagina p = ( Pagina ) oa;

            // cria o arquivo
            File file = new File( scoPath + "/sco_" + p.getId() + ".html" );
            
            // copia o template para o arquivo correto
            FileUtils.copyFile( new File( scoPath + "/template_sco.html" ) , file );


            // buffer para o conteúdo do arquivo
            StringBuilder conteudo = new StringBuilder();

            // lê o arquivo
            FileReader fr = new FileReader( file );
            char[] buffer = new char[ 2048 ];
            int b = 0;

            while ( ( b = fr.read( buffer, 0, 2048 ) ) != -1 ) {
                conteudo.append( buffer );
            }
            fr.close();

            String conteudoTransformado = transformarConteudo( p.getConteudoHtml() );

            // procura pelo {conteudo} e coloca o conteudo do html
            String novoConteudo = conteudo.toString().replace( "{conteudo}", conteudoTransformado );
            
            // quando é do tipo scorm, ainda precisa criar o título e o rodapé
            if ( tipo.equals( "scorm" ) ) {

                // título
                novoConteudo = novoConteudo.replace( "{titulo}", p.getTitulo() );


                // relações
                if ( material.getLayout().equals( LayoutMaterial.PCLCBN ) ||
                        material.getLayout().equals( LayoutMaterial.PCLCBN ) ) {

                    boolean temRelacoes = false;
                    StringBuilder sb = new StringBuilder();

                    sb.append( "<div style=\"font-size: 20px;\">" +
                            rs.getString( "servlets.exportacao.lblRelacoes" ) +
                            " " + p.getTitulo() + ":</div>" );
                    sb.append( "<ul>" );

                    for ( ConceitoComPagina cp : p.getConceitosComPaginas() ) {

                        for ( ConceitoComConceito cc : cp.getConceito().getConceitos() ) {
                            for ( ConceitoComPagina cpp : cc.getConceitoQueContem().getConceitosComPaginas() ) {
                                temRelacoes = true;
                                sb.append( "<li>" + cc.getRelacaoUsuario() +
                                        " <a href=\"sco_" + cpp.getPagina().getId() + ".html\">" +
                                        cpp.getConceito().getTitulo() + "</a></li>" );
                            }
                        }

                        for ( RelacaoMapaConceito rm : cp.getConceito().getConceitosOrigem() ) {
                            for ( ConceitoComPagina cpp : rm.getConceitoDestino().getConceitosComPaginas() ) {
                                temRelacoes = true;
                                sb.append( "<li>" + rm.getRelacaoUsuario() +
                                        " <a href=\"sco_" + cpp.getPagina().getId() + ".html\">" +
                                        cpp.getConceito().getTitulo() + "</a></li>" );
                            }
                        }

                    }

                    sb.append( "</ul>" );

                    if ( temRelacoes )
                        novoConteudo = novoConteudo.replace( "{rodape}", sb.toString() );
                    else
                        novoConteudo = novoConteudo.replace( "{rodape}", "" );

                } else {
                    novoConteudo = novoConteudo.replace( "{rodape}", "" );
                }

            }

            FileWriterWithEncoding fw = new FileWriterWithEncoding( file, Charset.forName( "UTF-8" ) );
            fw.write( novoConteudo );
            fw.close();

        }

    }


    /*
     * Transforma o conteúdo, trocando acentos e trocando as mídias pelo
     * caminho correto.
     */
    private String transformarConteudo( String conteudo ) {

        // prepara a acentuação
        conteudo = Uteis.escapeAcentos( conteudo );

        // prepara as tags
        conteudo = prepararTags( conteudo );

        return conteudo;

    }


    /*
     * Prepara as tags das imagens, vídeos e sons.
     */
    private String prepararTags( String conteudo ) {

        int ultimo = 0;

        while( true ) {

            int p = conteudo.indexOf( "<img", ultimo );

            if ( p == -1 ) {
                break;
            }

            int f = conteudo.indexOf( "src=\"", p );
            int t = conteudo.indexOf( "\"", f + 5 );
            String src = conteudo.substring( f + 5, t );

            if ( src.contains( "tipo=imagem" ) ) {

                String arquivo = src.substring( src.lastIndexOf( "=" ) + 1 );
                String troca = "../assets/imagens/" + arquivo;
                conteudo = conteudo.replace( src, troca );

                // atualiza o t para refletir as mudanças no conteúdo
                t = src.length() - troca.length();

            } else if ( src.equals( "imagens/previewVideo.png" ) ) {

                int fi = conteudo.indexOf( "id=\"", p );
                int ti = conteudo.indexOf( "\"", fi + 4 );
                String idVideo = conteudo.substring( fi + 4, ti );
                long id = Long.parseLong( idVideo.substring( idVideo.lastIndexOf( "-" ) + 1 ) );

                Video v = ( Video ) ht.load( Video.class, id );

                String tag = conteudo.substring( p, conteudo.indexOf( ">", p ) + 1 );
                String troca = "";

                if ( tipo.equals( "html" ) ) {
                    troca = "<div id=\"" + idVideo + "\"></div>" +
                        "<script type=\"text/javascript\">"+
                            "var so = new SWFObject('../js/player.swf','mpl','425','300','9');"+
                            "so.addParam('allowfullscreen','true');" +
                            "so.addParam('allowscriptaccess','always');" +
                            "so.addParam('wmode','opaque');" +
                            "so.addVariable('file','../assets/videos/" + v.getNomeArquivo() + "');" +
                            "so.write('" + idVideo + "');" +
                        "</script>";
                } else if ( tipo.equals( "scorm" ) ) {
                    troca = "<div id=\"" + idVideo + "\">" +
                        "<embed id=\"mp-" + idVideo + "\" " +
                            "width=\"425\" height=\"300\" " +
                            "flashvars=\"file=../assets/videos/" + v.getNomeArquivo() + "\" " +
                            "wmode=\"opaque\" allowscriptaccess=\"always\" " +
                            "allowfullscreen=\"true\" quality=\"high\" " +
                            "name=\"mp-" + idVideo + "\" style=\"\" " +
                            "src=\"../js/player.swf\" " +
                            "type=\"application/x-shockwave-flash\"/>" +
                        "</div>";
                }

                conteudo = conteudo.replace( tag, troca );

                t = troca.length() - tag.length();

            } else if ( src.equals( "imagens/previewSom.png" ) ) {

                int fi = conteudo.indexOf( "id=\"", p );
                int ti = conteudo.indexOf( "\"", fi + 4 );
                String idSom = conteudo.substring( fi + 4, ti );
                long id = Long.parseLong( idSom.substring( idSom.lastIndexOf( "-" ) + 1 ) );

                Som s = ( Som ) ht.load( Som.class, id );

                String tag = conteudo.substring( p, conteudo.indexOf( ">", p ) + 1 );
                String troca = "";

                if ( tipo.equals( "html" ) ) {
                    troca = "<div id=\"" + idSom + "\"></div>" +
                        "<script type=\"text/javascript\">"+
                            "var so = new SWFObject('../js/player.swf','mpl','425','24','9');"+
                            "so.addParam('allowfullscreen','true');" +
                            "so.addParam('allowscriptaccess','always');" +
                            "so.addParam('wmode','opaque');" +
                            "so.addVariable('file','../assets/sons/" + s.getNomeArquivo() + "');" +
                            "so.write('" + idSom + "');" +
                        "</script>";
                } else if ( tipo.equals( "scorm" ) ) {
                    troca = "<div id=\"" + idSom + "\">" +
                        "<embed id=\"mp-" + idSom + "\" " +
                            "width=\"425\" height=\"24\" " +
                            "flashvars=\"file=../assets/sons/" + s.getNomeArquivo() + "\" " +
                            "wmode=\"opaque\" allowscriptaccess=\"always\" " +
                            "allowfullscreen=\"true\" quality=\"high\" " +
                            "name=\"mp-" + idSom + "\" style=\"\" " +
                            "src=\"../js/player.swf\" " +
                            "type=\"application/x-shockwave-flash\"/>" +
                        "</div>";
                }

                conteudo = conteudo.replace( tag, troca );

                t = troca.length() - tag.length();

            }

            ultimo = t;

        }

        return conteudo;

    }


    /*
     * Gerador dos dados.
     */
    private String gerarDados( Material m ) {

        StringBuilder sb = new StringBuilder();

        sb.append( "var dadosMaterial = {" );
        extrairDados( m, sb );
        sb.append( "]}" );

        // remove vírgulas a mais (IE não consegue interpretar)
        return sb.toString().replace( "},]", "}]" );

    }


    /*
     * Extrai os dados da estrutura do material.
     */
    private void extrairDados( ObjetoAprendizagem oa, StringBuilder sb ) {

        if ( oa instanceof Material ) {

            Material m = ( Material ) oa;
            boolean parada = false;

            sb.append( "id: 'arvoreMaterial', " +
                    "tipo: 'material', " +
                    "titulo: '" + m.getTitulo() + "', " +
                    "strings: {" +
                        "btnExpandir: '" + rs.getString( "servlets.exportacao.lblExpandir" ) + "', " +
                        "btnContrair: '" + rs.getString( "servlets.exportacao.lblContrair" ) + "', " +
                        "btnProximo: '" + rs.getString( "servlets.exportacao.lblProximo" ) + "', " +
                        "btnAnterior: '" + rs.getString( "servlets.exportacao.lblAnterior" ) + "', " +
                        "lblRelacoes: '" + rs.getString( "servlets.exportacao.lblRelacoes" ) + "'" +
                    "}, " +
                    "configs: {" );

            switch ( m.getLayout() ) {
                case PCLCBN:
                    sb.append( "mostrarControles: true, " +
                        "gerarRelacionamentos: true" );
                    break;
                case PCLSBN:
                    sb.append( "mostrarControles: false, " +
                        "gerarRelacionamentos: true" );
                    break;
                case PSLCBN:
                    sb.append( "mostrarControles: true, " +
                        "gerarRelacionamentos: false" );
                    break;
                case PSLSBN:
                    sb.append( "mostrarControles: false, " +
                        "gerarRelacionamentos: false" );
                    break;
            }


            sb.append( "}, " +
                    "componentes: [" );

            for ( MaterialComConceito mc : m.getMateriaisComConceitos() ) {
                parada = true;
                Conceito c = mc.getConceito();
                sb.append( jsonToConceito( c, mc.getOrdem() ) );
                extrairDados( c, sb );
                sb.append( "]}," );
            }

            for ( MaterialComGrupo mg : m.getMateriaisComGrupos() ) {
                parada = true;
                Grupo g = mg.getGrupo();
                sb.append( jsonToGrupo( g, mg.getOrdem() ) );
                extrairDados( g, sb );
                sb.append( "]}," );
            }

            if ( !parada )
                return;

        }

        if ( oa instanceof Conceito ) {

            Conceito c = ( Conceito ) oa;
            boolean parada = false;

            for ( ConceitoComPagina cp : c.getConceitosComPaginas() ) {
                parada = true;
                Pagina p = cp.getPagina();
                sb.append( jsonToPagina( p, cp.getOrdem() ) );
                sb.append( "]}," );
            }

            for ( ConceitoComConceito cc : c.getConceitos() ) {
                parada = true;
                Conceito ci = cc.getConceitoQueContem();
                sb.append( jsonToConceito( ci, cc.getOrdem() ) );
                extrairDados( ci, sb );
                sb.append( "]}," );
            }

            if ( !parada )
                return;

        }

        if ( oa instanceof Grupo ) {

            Grupo g = ( Grupo ) oa;
            boolean parada = false;

            for ( GrupoComPagina gp : g.getGruposComPaginas() ) {
                parada = true;
                Pagina p = gp.getPagina();
                sb.append( jsonToPagina( p, gp.getOrdem() ) );
                sb.append( "]}," );
            }

            for ( GrupoComGrupo gg : g.getGrupos() ) {
                parada = true;
                Grupo gi = gg.getGrupoQueContem();
                sb.append( jsonToGrupo( gi, gg.getOrdem() ) );
                extrairDados( gi, sb );
                sb.append( "]}," );
            }

            if ( !parada )
                return;

        }

    }


    /*
     * Métodos para criação de json a partir de objetos.
     */
    private String jsonToPagina( Pagina p, int ordem ) {

        StringBuilder dados = new StringBuilder();

        dados.append( "{" +
                "id: 'pagina-" + p.getId() + "', " +
                "tipo: 'pagina', " +
                "titulo: '" + p.getTitulo() + "', " +
                "endereco: 'sco_" + p.getId() + ".html', " +
                "ordem: " + ordem + ", " +
                "relacionamentos: [" );
        
        for ( ConceitoComPagina cp : p.getConceitosComPaginas() ) {
            
            for ( ConceitoComConceito cc : cp.getConceito().getConceitos() ) {
                for ( ConceitoComPagina cpp : cc.getConceitoQueContem().getConceitosComPaginas() ) {
                    dados.append( "{" +
                            "endereco: 'sco_" + cpp.getPagina().getId() + ".html', " +
                            "relacao: '" + cc.getRelacaoUsuario() + "'" +
                            "},"
                    );
                }
            }

            for ( RelacaoMapaConceito rm : cp.getConceito().getConceitosOrigem() ) {
                for ( ConceitoComPagina cpp : rm.getConceitoDestino().getConceitosComPaginas() ) {
                    dados.append( "{" +
                            "endereco: 'sco_" + cpp.getPagina().getId() + ".html', " +
                            "relacao: '" + rm.getRelacaoUsuario() + "'" +
                            "},"
                    );
                }
            }

        }
        
        return dados.toString();

    }

    private String jsonToConceito( Conceito c, int ordem ) {
        return "{" +
                "id: 'conceito-" + c.getId() + "', " +
                "tipo: 'conceito', " +
                "titulo: '" + c.getTitulo() + "', " +
                "ordem: " + ordem + ", " +
                "componentes: [";
    }

    private String jsonToGrupo( Grupo g, int ordem ) {
        return "{" +
                "id: 'grupo-" + g.getId() + "', " +
                "tipo: 'grupo', " +
                "titulo: '" + g.getTitulo() + "', " +
                "ordem: " + ordem + ", " +
                "componentes: [";
    }

    /*
     * Classe interna para implementação da ordenação de objetos quando gerado
     * o pacote scorm. Isso é necessário pois a ordem do pacote scorm
     * precisa ser definida explicitamente, ao contrário do pacote html onde
     * a ordem é gerada nos dados JSON e é analisada pelo script do pacote.
     */
    private class ObjetoAprendizagemOrdenado implements Comparable {

        private ObjetoAprendizagem oa;
        private int ordem;

        public ObjetoAprendizagemOrdenado( ObjetoAprendizagem oa, int ordem ) {
            this.oa = oa;
            this.ordem = ordem;
        }

        @Override
        public int compareTo( Object o ) {

            if ( o instanceof ObjetoAprendizagemOrdenado ) {

                ObjetoAprendizagemOrdenado oao = ( ObjetoAprendizagemOrdenado ) o;

                if ( this.equals( oao ) ) {
                    return 0;
                } else {
                    if ( this.ordem < oao.ordem ) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

            } else {
                return -1;
            }

        }

        @Override
        public boolean equals( Object obj ) {
            if ( obj == null ) {
                return false;
            }
            if ( getClass() != obj.getClass() ) {
                return false;
            }
            final ObjetoAprendizagemOrdenado other = ( ObjetoAprendizagemOrdenado ) obj;
            if ( this.oa != other.oa && ( this.oa == null || !this.oa.equals( other.oa ) ) ) {
                return false;
            }
            if ( this.ordem != other.ordem ) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + ( this.oa != null ? this.oa.hashCode() : 0 );
            hash = 17 * hash + this.ordem;
            return hash;
        }

    }

}
