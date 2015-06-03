package service.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import service.CommonService;
import service.MatchService;
import service.PlayerService;
import service.ServiceFactory;
import service.TeamService;

/**
 * RMI service 服务工厂
 * 
 * created by JaneLDQ on 2015年5月31日 下午8:26:18
 */
public class ServiceFactoryImpl extends UnicastRemoteObject implements ServiceFactory{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 单例实例
	 */
	private static ServiceFactory serviceFactory = null;
	
	/**
	 * 默认ip
	 */
	public static String address = null;
	
	/**
	 * 默认端口
	 */
	public static String port = null;
	
	// TODO ------- SrviceFactoryImpl main test------------------
	public static void main(String[] args) {
		try {
			ImageIcon icon = ServiceFactoryImpl.getInstance().getTeamService().getTeamLogoByAbbr("ATL");
			JLabel jl = new JLabel(icon);
			JFrame jf = new JFrame();
			jf.setSize(400, 400);
			jf.getContentPane().add(jl);
			jf.setVisible(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
    static{
    	Properties prop = new Properties();
    	try{
    		InputStream in = new BufferedInputStream(new FileInputStream("service.properties"));
    		prop.load(in);
    		address = prop.getProperty("address");
    		port = prop.getProperty("port");
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
    
	/**
	 * 私有化构造方法
	 * @throws RemoteException
	 */
	private ServiceFactoryImpl() throws RemoteException {
		super();
	}
	
	/**
	 * 获得实例
	 * @return
	 */
	public static ServiceFactory getInstance() {
		try{
			serviceFactory = (ServiceFactory)Naming.lookup("rmi://"+address+":"+port+"/ServiceFactory");
		}catch(Exception e){
			System.out.println("Get Service error!");
		}
		return serviceFactory;
	}

	public PlayerService getPlayerService() throws RemoteException {
		return new PlayerServiceImpl();
	}

	public TeamService getTeamService() throws RemoteException {
		return new TeamServiceImpl();
	}

	public MatchService getMatchService() throws RemoteException {
		return new MatchServiceImpl();
	}

	public CommonService getCommonService() throws RemoteException {
		return new CommonServiceImpl();
	}


}
