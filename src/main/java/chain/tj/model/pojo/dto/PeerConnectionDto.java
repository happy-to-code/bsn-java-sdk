package chain.tj.model.pojo.dto;

/**
 * @Describe:ip地址和端口
 * @Author: zhangyifei
 * @Date: 2020/6/3 16:40
 */
public class PeerConnectionDto {

    /**
     * ip地址
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public PeerConnectionDto(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }
}
