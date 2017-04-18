package study;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {
	private static final int BUFFER = 2048;

	/**
	 * 解压文件到指定路径
	 * 
	 * @param filePath
	 * @param upZipPath
	 * @return 返回解压的文件集合
	 */
	public static List<File> unZip(String filePath, String upZipPath) {
		List<File> list = new ArrayList<File>();
		int count = -1;
		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		// 生成指定的保存目录
		String savePath = upZipPath;
		if (!new File(savePath).exists()) {
			new File(savePath).mkdirs();
		}

		try {
			ZipFile zipFile = new ZipFile(filePath, "GBK");
			Enumeration enu = zipFile.getEntries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();
				if (zipEntry.isDirectory()) {
					new File(savePath + "/" + zipEntry.getName()).mkdirs();
					continue;
				}
				if (zipEntry.getName().indexOf("/") != -1) {
					new File(savePath + "/" + zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf("/")))
							.mkdirs();
				}
				is = zipFile.getInputStream(zipEntry);
				file = new File(savePath + "/" + zipEntry.getName());
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFFER);

				byte buf[] = new byte[BUFFER];
				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}

				bos.flush();
				fos.close();
				is.close();
				list.add(file);
			}

			zipFile.close();
			return list;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return list;
		}
	}

	/**
	 * RAR 需要配置rar路径
	 * 
	 * @param filePath
	 * @param unRarPath
	 *            路径要唯一，否则获取文件列表会出错
	 * @return
	 */
	public static int unRar(String filePath, String unRarPath) {
		int result = -99;
		if (!(new File(unRarPath).exists())) {
			new File(unRarPath).mkdirs();
		}
		try {
			// String cmd = GlobalConfig.getConfigValue("cmd.path");
			String cmd = "\"C:\\Program Files\\WinRAR\\Rar.exe\"";
			String unrarCmd = cmd + " e -r -o+ " + filePath + " " + unRarPath;
			Runtime rt = Runtime.getRuntime();

			Process pre = rt.exec(unrarCmd);
			while (result == -99) {
				try {
					Thread.sleep(1000L);
					result = pre.exitValue();
				} catch (Exception e) {
					// TODO: handle exception
					result = -99;
				}
			}
			InputStreamReader isr = new InputStreamReader(pre.getInputStream());
			BufferedReader bf = new BufferedReader(isr);
			String line = null;
			while ((line = bf.readLine()) != null) {
				line = line.trim();
				if ("".equals(line)) {
					continue;
				}
				System.out.println(line);
			}
			bf.close();
			if (result != 0) {
				System.out.println("unRar " + pre.exitValue());
			}
			// 杀死进程 退出
			// pre.destroy();
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage() + ": " + e.getStackTrace());
			return -2;
		}
	}

	/***
	 * 将多个文件打成压缩包
	 * 
	 * @param list
	 *            需打包的文件路径集合
	 * @param zipfilename
	 *            压缩包名称
	 */
	public static void listToZip(List<String> list, String zipfilename) {
		FileInputStream is = null;
		String path = "";
		File file = null;
		ZipOutputStream zos = null;
		try {
			if (list != null && list.size() > 0) {
				// String uri = GlobalConfig.getConfigValue("zipFile.path");
				String uri = "D:/ZIP";
				File f = new File(uri);
				if (!f.exists()) {
					f.mkdirs();
				}
				zipfilename = uri + zipfilename;
				// 创建zip文件输出流
				zos = new ZipOutputStream(new FileOutputStream(new File(zipfilename)));
				zos.setEncoding("GBK");
				for (int i = 0; i < list.size(); i++) {
					path = list.get(i);
					file = new File(path);
					if (file.exists()) {
						// 创建源文件输入流
						is = new FileInputStream(file);
						zos.putNextEntry(new ZipEntry(file.getName()));
						byte[] buf = new byte[BUFFER];
						int length = -1;
						while ((length = is.read(buf)) != -1) {
							zos.write(buf, 0, length);
							zos.flush();
						}
						zos.closeEntry();
						is.close();
					} else {
						System.out.println("源文件不存在");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/***
	 * 将多个文件打成压缩包(压缩包内文件名称由参数中传入)
	 * 
	 * @param list
	 *            需打包的文件信息集合
	 * @param zipfilename
	 *            压缩包名称
	 */
	public static void listMapToZip(List<Map<String, Object>> list, String zipfilename) {
		FileInputStream is = null;
		String path = "";
		File file = null;
		ZipOutputStream zos = null;
		try {
			if (list != null && list.size() > 0) {
				// String uri = GlobalConfig.getConfigValue("zipFile.path");
				String uri = "D:/ZIP";
				File f = new File(uri);
				if (!f.exists()) {
					f.mkdirs();
				}
				zipfilename = uri + zipfilename;
				// 创建zip文件输出流
				zos = new ZipOutputStream(new FileOutputStream(new File(zipfilename)));
				zos.setEncoding("GBK");
				for (Map map : list) {
					path = map.get("filePath") + "";
					file = new File(path);
					if (file.exists()) {
						// 创建源文件输入流
						is = new FileInputStream(file);
						zos.putNextEntry(new ZipEntry(map.get("fileName") + ""));
						byte[] buf = new byte[BUFFER];
						int length = -1;
						while ((length = is.read(buf)) != -1) {
							zos.write(buf, 0, length);
							zos.flush();
						}
						zos.closeEntry();
						is.close();
					} else {
						System.out.println("源文件不存在");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws Exception {
		// List<String> list = new ArrayList<String>();
		// list.add("D:/File/img/tempImg/7f7ffd45856be6aa.txt");
		// list.add("D:/File/img/tempImg/63f8959465093ec0.jpeg");
		// list.add("D:/File/img/tempImg/815b476dc8cf790e.txt");
		// list.add("D:/File/img/tempImg/4451519220dad91a.txt");
		// ZipUtil.listToZip(list, "测试.zip");

		System.out.println(ZipUtil.unRar("F:\\880-31837595_16_身份证99.rar", "F:/upzip/"));

	}
}