package com.hifumi123.ga4j.chromosome;

/**
 * 格雷码编码染色体。
 * 
 * @author Hifumi123
 *
 */
public class GrayCodeChromosome extends BinaryChromosome {

	public GrayCodeChromosome(int length) {
		super(length);
	}
	
	public GrayCodeChromosome(boolean[] genes) {
		super(genes);
	}

	@Override
	public int decode(int begin, int end) {
		//格雷码转成二进制码
		boolean[] bs = new boolean[end - begin];
		bs[0] = getGene(begin);
		for (int i = 1; i < bs.length; i++)
			bs[i] = bs[i - 1] ^ getGene(begin + i);
		
		int r = 0;
		int o = 1;
		for (int i = bs.length - 1; i >= 0; i--) {
			if (bs[i])
				r += o;
			
			o *= 2;
		}
		
		return r;
	}
}
