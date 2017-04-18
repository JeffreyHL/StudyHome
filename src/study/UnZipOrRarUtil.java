package study;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * @Title UnZipOrRarUtil
 * @Description ZIP/RAR文件解压工具类
 * @Author HL
 * @Date 2017年3月6日 下午5:09:24
 */
public class UnZipOrRarUtil {

	// 编码格式
	private static final Charset CHARSET = Charset.forName("GBK");
	// 压缩包内文件列表
	private static final List<ZipEntry> ENTRIES = new ArrayList<ZipEntry>();

	/**
	 * 读取压缩文件中的文件名
	 * 
	 * @param zipFilePath
	 *            压缩文件路径
	 * @return 文件名列表
	 */
	public static List<String> readZipFile(String zipFilePath) {
		List<String> list = new ArrayList<String>();
		InputStream buffIS = null; // 缓冲输入流
		ZipInputStream zipIS = null; // zip输入流
		try {
			buffIS = new BufferedInputStream(new FileInputStream(zipFilePath));
			zipIS = new ZipInputStream(buffIS, CHARSET);
			java.util.zip.ZipEntry zipEntry; // 压缩文件内的具体文件对象
			while ((zipEntry = zipIS.getNextEntry()) != null) {
				// 非文件夹，获取文件名（包含父级文件夹）
				if (!zipEntry.isDirectory()) {
					list.add(zipEntry.getName());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (null != zipIS)
					zipIS.close();
				if (null != buffIS)
					buffIS.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return list;
	}

	/**
	 * 解压zip文件
	 * 
	 * @param zipFilePath
	 *            zip文件路径
	 * @param outputPath
	 *            输出路径
	 */
	@SuppressWarnings({ "unchecked" })
	public static void unZip(String zipFilePath, String outputPath) {
		ZipFile zipFile = null; // zip文件

		// 文件夹不存在则创建
		File file = new File(outputPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		try {
			zipFile = new ZipFile(zipFilePath);
			Enumeration<ZipEntry> entryEnum = (Enumeration<ZipEntry>) zipFile.getEntries();
			while (entryEnum.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
				// 文件夹处理
				if (zipEntry.isDirectory()) {
					String fileName = zipEntry.getName().substring(0, zipEntry.getName().length() - 1);
					String dicPath = outputPath + File.separator + fileName;
					File dicFile = new File(dicPath);
					dicFile.mkdir();
				} else {
					ENTRIES.add(zipEntry);
				}
			}
			unZip(zipFile, ENTRIES, outputPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压rar文件
	 * 
	 * @param zipFilePath
	 *            zip文件路径
	 * @param outputPath
	 *            输出路径
	 */
	@SuppressWarnings({ "unchecked" })
	public static void unRar(String zipFilePath, String outputPath) {
		ZipFile zipFile = null; // zip文件

		// 文件夹不存在则创建
		File file = new File(outputPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		try {
			zipFile = new ZipFile(zipFilePath);
			Enumeration<ZipEntry> entryEnum = (Enumeration<ZipEntry>) zipFile.getEntries();
			while (entryEnum.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
				// 文件夹处理
				if (zipEntry.isDirectory()) {
					String fileName = zipEntry.getName().substring(0, zipEntry.getName().length() - 1);
					String dicPath = outputPath + File.separator + fileName;
					File dicFile = new File(dicPath);
					dicFile.mkdir();
				} else {
					ENTRIES.add(zipEntry);
				}
			}
			unZip(zipFile, ENTRIES, outputPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件解析
	 * 
	 * @param zipFile
	 *            zip文件
	 * @param entries
	 *            zip内容文件对象列表
	 * @param outputPath
	 *            输出路径
	 */
	private static void unZip(ZipFile zipFile, List<ZipEntry> entries, String outputPath) {
		Iterator<ZipEntry> it = ENTRIES.iterator();
		while (it.hasNext()) {
			ZipEntry zipEntry = it.next();
			MultiThreadEntry multiThreadEntry;
			try {
				multiThreadEntry = new MultiThreadEntry(zipFile, zipEntry, outputPath);
				Thread thread = new Thread(multiThreadEntry);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// TEST
	public static void main(String[] args) throws Exception {
		List<String> list = readZipFile("F:\\880-31837595_16_身份证99.zip");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		UnZipOrRarUtil.unRar("F:\\880-31837595_16_身份证99.rar", "F:\\Hello2_2_2");
		// UnZipOrRarUtil.unZip("F:\\880-31837595_16_身份证99.rar",
		// "F:\\Hello2_2_2");
	}
}

/**
 * @Title MultiThreadEntry
 * @Description 多线程处理文件写出
 * @Author HL
 * @Date 2017年3月7日 下午2:07:16
 */
class MultiThreadEntry implements Runnable {
	public static final int BUFFER_SIZE = 4096;
	private BufferedInputStream buffIS = null; // 缓冲输入流
	private ZipEntry zipEntry = null; // zip文件对象
	private String outputPath = null; // 输出路径

	public MultiThreadEntry(ZipFile zipFile, ZipEntry zipEntry, String outputPath) throws IOException {
		this.zipEntry = zipEntry;
		this.outputPath = outputPath;
		buffIS = new BufferedInputStream(zipFile.getInputStream(zipEntry));
	}

	@Override
	public void run() {
		try {
			unZipFiles(zipEntry, outputPath);
		} catch (Exception e) {
			try {
				if (buffIS != null)
					buffIS.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		} finally {
			try {
				if (buffIS != null)
					buffIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析文件取出
	 * 
	 * @param zipEntry
	 *            zip文件对象
	 * @param outputPath
	 *            输出路径
	 */
	public void unZipFiles(ZipEntry zipEntry, String outputPath) {
		byte[] data = new byte[BUFFER_SIZE];
		FileOutputStream fileOS = null;
		BufferedOutputStream buffOS = null;

		try {
			fileOS = new FileOutputStream(outputPath + File.separator + zipEntry.getName());
			if (!zipEntry.isDirectory()) { // 非文件夹
				buffOS = new BufferedOutputStream(fileOS, BUFFER_SIZE); // 缓冲输出流
				int count = 0;
				while ((count = buffIS.read(data, 0, BUFFER_SIZE)) != -1) {
					buffOS.write(data, 0, count);
				}
				buffOS.flush();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOS != null)
					fileOS.close();
				if (buffOS != null)
					buffOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
