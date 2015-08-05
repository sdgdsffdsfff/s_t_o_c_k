package com.sumur.java.test.pattern.demo2;

import org.junit.Test;

/**
 * 适配器模式(此处示例为对象适配器,在adapter中注入对象,更加灵活)
 * 使用场景：有动机地修改一个正常运行的系统的接口，这时应该考虑使用适配器模式。
 * 注意事项：适配器不是在详细设计时添加的，而是解决正在服役的项目的问题
 * @author Li mao sen
 *
 */
public class TestDemo05 {
	@Test
	public void test1(){
		MyPlayer mp = new MyPlayer();
		mp.play("mov");
		mp.play("mp4");
		mp.play("flac");
	}
}


interface MediaPlayer{
	//原本play方法只支持mov格式播放,现在需扩展为同时支持mp4以及flac
	void play(String playType);
}

interface AdvanceMediaPlayer{
	void playMp4();
	void playFlac();
}

class Mp4Player implements AdvanceMediaPlayer{
	@Override
	public void playMp4() {
		System.out.println("mp4 playing");
	}
	@Override
	public void playFlac() {
	}
}

class FlacPlayer implements AdvanceMediaPlayer{
	@Override
	public void playMp4() {
	}
	@Override
	public void playFlac() {
		System.out.println("flac playing");
	}
}

class MediaAdapter implements MediaPlayer{
	AdvanceMediaPlayer advanceMediaPlayer;
	public MediaAdapter(String playType){
		if("mp4".equalsIgnoreCase(playType)){
			advanceMediaPlayer = new Mp4Player();
		}else if("flac".equalsIgnoreCase(playType)){
			advanceMediaPlayer = new FlacPlayer();
		}else{
		}
	}
	
	@Override
	public void play(String playType){
		if("mp4".equalsIgnoreCase(playType)){
			advanceMediaPlayer.playMp4();
		}else if("flac".equalsIgnoreCase(playType)){
			advanceMediaPlayer.playFlac();
		}else{
		}
	}
}

class MyPlayer implements MediaPlayer{
	MediaAdapter mediaAdapter;

	@Override
	public void play(String playType) {
		if("mov".equalsIgnoreCase(playType)){
			System.out.println("mov playing");
		}else{
			mediaAdapter= new MediaAdapter(playType);
			mediaAdapter.play(playType);
		}
	}

}




