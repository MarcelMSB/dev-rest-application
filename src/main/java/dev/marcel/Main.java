package dev.marcel;

import org.wildfly.swarm.Swarm;

import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String... args) throws Exception {

        // Cria uma instância do Swarm
        Swarm swarm = new Swarm(args);

        // Inicia e faz o deploy da aplicação
        swarm.start();
        swarm.deploy();
    }

    /**
     * Modificação para apenas retornar um arquivo de configuração .yml para a
     * aplicação, baseada na linha de publicação;
     *
     * @return String
     */
    private static String getAppConfigName() {
        final String deploy = System.getenv("DEPLOY") != null ? System.getenv("DEPLOY") : System.getProperty("DEPLOY");
        return deploy + "-config.yml";
    }

    static void configure(Swarm swarm, Configuration configuration) throws IOException {
        swarm.withConfig(configuration.loadAppConfiguration(getAppConfigName()))
                .withConfig(configuration.loadGenericConfiguration());
    }

    // Cada método nesta interface corresponde à um ou mais
    // arquivos de configuração que serão aplicados na instância do WildFly Swarm
    public interface Configuration {

        // Neste arquivo estão as configurações padrões para as aplicações que são mantidas pelo P&D
        // Ficam os endereços dos serviços genéricos como user accounts, licenses, etc.
        // Caso a aplicação não utilize nenhum destes recursos, não é necessário carregar esta configuração
        URL loadGenericConfiguration();

        // Carrega todas as configurações existentes dentro do diretório chave da aplicação no Consul
        // Ex: namings.yml, my_app.yml, datasources.yml, etc
        URL loadAppConfiguration();

        // Pode ser especificado individualmente as configurações que devem ser carregadas
        // Ou ainda
        // Podemos dizer que não queremos carregar uma determinada configuração
        // Ex: "-namings.yml" (basta colocar o sinal - antes do nome do arquivo)
        URL loadAppConfiguration(String... configs);
    }

}
